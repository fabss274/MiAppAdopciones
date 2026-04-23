package adopciones.v01.services.formulario;

import adopciones.v01.dto.formulario.FormularioDTO;
import adopciones.v01.enums.tipoFormulario;
import adopciones.v01.models.formularios.FormularioModel;
import adopciones.v01.models.refugios.RefugioModel;
import adopciones.v01.repositories.formularios.FormularioRepository;
import adopciones.v01.repositories.refugios.RefugioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FormularioService {
    private final FormularioRepository formularioRepo;
    private final RefugioRepository refugioRepo;

    // ***************** CRUD

    @Transactional(readOnly = true)
    public List<FormularioDTO> listarFormularios() {
        return formularioRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FormularioDTO crearFormulario(FormularioDTO dto) {
        RefugioModel refugio = refugioRepo.findById(dto.getRefugioId())
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado (id: " + dto.getRefugioId() + ")"));

        // Un refugio no puede tener dos formularios activos del mismo tipo
        if (formularioRepo.existsByRefugioAndTipoFormularioAndActivo(refugio, dto.getTipoFormulario(), true)) {
            throw new RuntimeException("El refugio ya tiene un formulario activo de tipo " + dto.getTipoFormulario());
        }

        FormularioModel formulario = new FormularioModel();
        formulario.setTipoFormulario(dto.getTipoFormulario());
        formulario.setRefugio(refugio);
        formulario.setActivo(true); // activo por defecto al crear
        formulario.setCreated_at(LocalDateTime.now());
        formulario.setUpdated_at(LocalDateTime.now());

        return toDTO(formularioRepo.save(formulario));
    }

    @Transactional
    public FormularioDTO editarFormulario(Long id, FormularioDTO dto) {
        FormularioModel formulario = formularioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Formulario no encontrado (id: " + id + ")"));

        RefugioModel refugio = refugioRepo.findById(dto.getRefugioId())
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado (id: " + dto.getRefugioId() + ")"));

        // Si cambia el tipo o el refugio, validar que no haya conflicto con otro activo
        boolean cambiaTipo = formulario.getTipoFormulario() != dto.getTipoFormulario();
        boolean cambiaRefugio = !formulario.getRefugio().getId().equals(dto.getRefugioId());

        if (dto.isActivo() && (cambiaTipo || cambiaRefugio)) {
            // Buscar si ya existe otro formulario activo del mismo tipo para ese refugio
            Optional<FormularioModel> conflicto = formularioRepo
                    .findByRefugioAndTipoFormularioAndActivo(refugio, dto.getTipoFormulario(), true);

            if (conflicto.isPresent() && !conflicto.get().getId().equals(id)) {
                throw new RuntimeException("El refugio ya tiene un formulario activo de tipo " + dto.getTipoFormulario());
            }
        }

        formulario.setTipoFormulario(dto.getTipoFormulario());
        formulario.setRefugio(refugio);
        formulario.setActivo(dto.isActivo());
        formulario.setUpdated_at(LocalDateTime.now());

        return toDTO(formularioRepo.save(formulario));
    }

    @Transactional
    public boolean eliminarFormulario(Long id) {
        if (!formularioRepo.existsById(id)) {
            return false;
        }
        formularioRepo.deleteById(id);
        return true;
    }

    // Activar / desactivar sin editar todo el formulario
    @Transactional
    public FormularioDTO toggleActivo(Long id) {
        FormularioModel formulario = formularioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Formulario no encontrado (id: " + id + ")"));

        boolean nuevoEstado = !formulario.isActivo();

        // Si se va a activar, verificar que no haya otro activo del mismo tipo en el mismo refugio
        if (nuevoEstado) {
            Optional<FormularioModel> conflicto = formularioRepo
                    .findByRefugioAndTipoFormularioAndActivo(formulario.getRefugio(), formulario.getTipoFormulario(), true);

            if (conflicto.isPresent() && !conflicto.get().getId().equals(id)) {
                throw new RuntimeException("Ya existe un formulario activo de tipo "
                        + formulario.getTipoFormulario() + " para este refugio");
            }
        }

        formulario.setActivo(nuevoEstado);
        formulario.setUpdated_at(LocalDateTime.now());
        return toDTO(formularioRepo.save(formulario));
    }

    // ***************** BUSQUEDAS

    @Transactional(readOnly = true)
    public Optional<FormularioDTO> buscarPorId(Long id) {
        return formularioRepo.findById(id).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<FormularioDTO> buscarPorRefugio(Long refugioId) {
        RefugioModel refugio = refugioRepo.findById(refugioId)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado (id: " + refugioId + ")"));
        return formularioRepo.findByRefugio(refugio)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FormularioDTO> buscarPorTipo(tipoFormulario tipo) {
        return formularioRepo.findByTipoFormulario(tipo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FormularioDTO> buscarActivos() {
        return formularioRepo.findByActivo(true)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FormularioDTO> buscarPorRefugioYActivo(Long refugioId, boolean activo) {
        RefugioModel refugio = refugioRepo.findById(refugioId)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado (id: " + refugioId + ")"));
        return formularioRepo.findByRefugioAndActivo(refugio, activo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ***************** TRADUCTOR

    private FormularioDTO toDTO(FormularioModel model) {
        FormularioDTO dto = new FormularioDTO();
        dto.setId(model.getId());
        dto.setTipoFormulario(model.getTipoFormulario());
        dto.setRefugioId(model.getRefugio().getId());
        dto.setActivo(model.isActivo());
        dto.setCreated_at(model.getCreated_at());
        dto.setUpdated_at(model.getUpdated_at());
        return dto;
    }
}
