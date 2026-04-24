package adopciones.v01.services.formulario;

import adopciones.v01.dto.pregunta.PreguntaDTO;
import adopciones.v01.enums.tipoSintaxisPregunta;
import adopciones.v01.models.formularios.FormularioModel;
import adopciones.v01.models.formularios.PreguntaModel;
import adopciones.v01.repositories.formularios.FormularioRepository;
import adopciones.v01.repositories.formularios.PreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PreguntaService {
    private final PreguntaRepository preguntaRepo;
    private final FormularioRepository formularioRepo;

    // ***************** CRUD

    @Transactional(readOnly = true)
    public List<PreguntaDTO> listarPreguntas() {
        return preguntaRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PreguntaDTO crearPregunta(PreguntaDTO dto) {
        FormularioModel formulario = formularioRepo.findById(dto.getFormularioId())
                .orElseThrow(() -> new RuntimeException("Formulario no encontrado (id: " + dto.getFormularioId() + ")"));

        if (!formulario.isActivo()) {
            throw new RuntimeException("No se pueden añadir preguntas a un formulario inactivo");
        }

        // Si no viene orden, se asigna al final
        if (dto.getOrden() == null) {
            dto.setOrden(preguntaRepo.findMaxOrdenByFormulario(formulario) + 1);
        }

        if (preguntaRepo.existsByFormularioAndOrden(formulario, dto.getOrden())) {
            throw new RuntimeException("Ya existe una pregunta con orden " + dto.getOrden() + " en este formulario");
        }

        PreguntaModel pregunta = new PreguntaModel();
        pregunta.setFormulario(formulario);
        pregunta.setPregunta(dto.getPregunta());
        pregunta.setTipoSintaxisPregunta(dto.getTipoSintaxisPregunta());
        pregunta.setOrden(dto.getOrden());
        pregunta.setObligatoria(dto.isObligatoria());

        return toDTO(preguntaRepo.save(pregunta));
    }

    @Transactional
    public PreguntaDTO editarPregunta(Long id, PreguntaDTO dto) {
        PreguntaModel pregunta = preguntaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada (id: " + id + ")"));

        FormularioModel formulario = formularioRepo.findById(dto.getFormularioId())
                .orElseThrow(() -> new RuntimeException("Formulario no encontrado (id: " + dto.getFormularioId() + ")"));

        // Validar orden si cambia
        if (dto.getOrden() != null &&
                !dto.getOrden().equals(pregunta.getOrden()) &&
                preguntaRepo.existsByFormularioAndOrdenAndIdNot(formulario, dto.getOrden(), id)) {
            throw new RuntimeException("Ya existe una pregunta con orden " + dto.getOrden() + " en este formulario");
        }

        pregunta.setFormulario(formulario);
        pregunta.setPregunta(dto.getPregunta());
        pregunta.setTipoSintaxisPregunta(dto.getTipoSintaxisPregunta());
        pregunta.setOrden(dto.getOrden());
        pregunta.setObligatoria(dto.isObligatoria());

        return toDTO(preguntaRepo.save(pregunta));
    }

    @Transactional
    public boolean eliminarPregunta(Long id) {
        if (!preguntaRepo.existsById(id)) {
            return false;
        }
        preguntaRepo.deleteById(id);
        return true;
    }

    // ***************** BUSQUEDAS

    @Transactional(readOnly = true)
    public Optional<PreguntaDTO> buscarPorId(Long id) {
        return preguntaRepo.findById(id).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<PreguntaDTO> buscarPorFormulario(Long formularioId) {
        FormularioModel formulario = formularioRepo.findById(formularioId)
                .orElseThrow(() -> new RuntimeException("Formulario no encontrado (id: " + formularioId + ")"));
        return preguntaRepo.findByFormularioOrderByOrdenAsc(formulario)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PreguntaDTO> buscarPorFormularioYObligatoria(Long formularioId, boolean obligatoria) {
        FormularioModel formulario = formularioRepo.findById(formularioId)
                .orElseThrow(() -> new RuntimeException("Formulario no encontrado (id: " + formularioId + ")"));
        return preguntaRepo.findByFormularioAndObligatoria(formulario, obligatoria)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PreguntaDTO> buscarPorTipo(tipoSintaxisPregunta tipo) {
        return preguntaRepo.findByTipoSintaxisPregunta(tipo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ***************** TRADUCTOR

    private PreguntaDTO toDTO(PreguntaModel model) {
        PreguntaDTO dto = new PreguntaDTO();
        dto.setId(model.getId());
        dto.setFormularioId(model.getFormulario().getId());
        dto.setPregunta(model.getPregunta());
        dto.setTipoSintaxisPregunta(model.getTipoSintaxisPregunta());
        dto.setOrden(model.getOrden());
        dto.setObligatoria(model.isObligatoria());
        return dto;
    }
}
