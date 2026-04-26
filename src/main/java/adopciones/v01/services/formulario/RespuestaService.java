package adopciones.v01.services.formulario;


import adopciones.v01.dto.respuesta.RespuestaDTO;
import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.formularios.PreguntaModel;
import adopciones.v01.models.formularios.RespuestaModel;
import adopciones.v01.repositories.adopciones.AcogidaRepository;
import adopciones.v01.repositories.adopciones.AdopcionRepository;
import adopciones.v01.repositories.formularios.PreguntaRepository;
import adopciones.v01.repositories.formularios.RespuestaRepository;
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
public class RespuestaService {
    private final RespuestaRepository respuestaRepo;
    private final PreguntaRepository preguntaRepo;
    private final AdopcionRepository adopcionRepo;
    private final AcogidaRepository acogidaRepo;

    // ***************** CRUD

    @Transactional(readOnly = true)
    public List<RespuestaDTO> listarRespuestas() {
        return respuestaRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RespuestaDTO crearRespuesta(RespuestaDTO dto) {
        if (dto.getAdopcionId() == null && dto.getAcogidaId() == null) {
            throw new RuntimeException("La respuesta debe estar vinculada a una adopción o una acogida");
        }
        if (dto.getAdopcionId() != null && dto.getAcogidaId() != null) {
            throw new RuntimeException("La respuesta no puede estar vinculada a adopción y acogida a la vez");
        }

        PreguntaModel pregunta = preguntaRepo.findById(dto.getPreguntaId())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada (id: " + dto.getPreguntaId() + ")"));

        validarContenidoRespuesta(dto.getRespuesta(), pregunta);

        RespuestaModel respuesta = new RespuestaModel();
        respuesta.setPregunta(pregunta);
        respuesta.setRespuesta(dto.getRespuesta());
        respuesta.setCreated_at(LocalDateTime.now());

        if (dto.getAdopcionId() != null) {
            AdopcionModel adopcion = adopcionRepo.findById(dto.getAdopcionId())
                    .orElseThrow(() -> new RuntimeException("Adopción no encontrada (id: " + dto.getAdopcionId() + ")"));

            if (respuestaRepo.existsByAdopcionAndPregunta(adopcion, pregunta)) {
                throw new RuntimeException("Ya existe una respuesta para esta pregunta en la adopción");
            }
            respuesta.setAdopcion(adopcion);
        } else {
            AcogidaModel acogida = acogidaRepo.findById(dto.getAcogidaId())
                    .orElseThrow(() -> new RuntimeException("Acogida no encontrada (id: " + dto.getAcogidaId() + ")"));

            if (respuestaRepo.existsByAcogidaAndPregunta(acogida, pregunta)) {
                throw new RuntimeException("Ya existe una respuesta para esta pregunta en la acogida");
            }
            respuesta.setAcogida(acogida);
        }

        return toDTO(respuestaRepo.save(respuesta));
    }

    @Transactional
    public RespuestaDTO editarRespuesta(Long id, RespuestaDTO dto) {
        RespuestaModel respuesta = respuestaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada (id: " + id + ")"));

        PreguntaModel pregunta = preguntaRepo.findById(dto.getPreguntaId())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada (id: " + dto.getPreguntaId() + ")"));

        validarContenidoRespuesta(dto.getRespuesta(), pregunta);

        respuesta.setPregunta(pregunta);
        respuesta.setRespuesta(dto.getRespuesta());

        return toDTO(respuestaRepo.save(respuesta));
    }

    @Transactional
    public boolean eliminarRespuesta(Long id) {
        if (!respuestaRepo.existsById(id)) {
            return false;
        }
        respuestaRepo.deleteById(id);
        return true;
    }

    // ***************** BUSQUEDAS

    @Transactional(readOnly = true)
    public Optional<RespuestaDTO> buscarPorId(Long id) {
        return respuestaRepo.findById(id).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<RespuestaDTO> buscarPorAdopcion(Long adopcionId) {
        AdopcionModel adopcion = adopcionRepo.findById(adopcionId)
                .orElseThrow(() -> new RuntimeException("Adopción no encontrada (id: " + adopcionId + ")"));
        return respuestaRepo.findByAdopcion(adopcion)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RespuestaDTO> buscarPorAcogida(Long acogidaId) {
        AcogidaModel acogida = acogidaRepo.findById(acogidaId)
                .orElseThrow(() -> new RuntimeException("Acogida no encontrada (id: " + acogidaId + ")"));
        return respuestaRepo.findByAcogida(acogida)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RespuestaDTO> buscarPorPregunta(Long preguntaId) {
        PreguntaModel pregunta = preguntaRepo.findById(preguntaId)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada (id: " + preguntaId + ")"));
        return respuestaRepo.findByPregunta(pregunta)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ***************** VALIDACION CONTENIDO

    private void validarContenidoRespuesta(String valor, PreguntaModel pregunta) {
        if (pregunta.isObligatoria() && (valor == null || valor.isBlank())) {
            throw new RuntimeException("La pregunta '" + pregunta.getPregunta() + "' es obligatoria");
        }
        if (valor == null || valor.isBlank()) return;

        switch (pregunta.getTipoSintaxisPregunta()) {
            case BOOLEAN -> {
                if (!valor.equalsIgnoreCase("true") && !valor.equalsIgnoreCase("false")) {
                    throw new RuntimeException("La respuesta debe ser 'true' o 'false' para preguntas de tipo BOOLEAN");
                }
            }
            case NUMBER -> {
                try {
                    Double.parseDouble(valor);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("La respuesta debe ser un número para preguntas de tipo NUMBER");
                }
            }
            case TEXT -> {}
        }
    }

    // ***************** TRADUCTOR

    private RespuestaDTO toDTO(RespuestaModel model) {
        RespuestaDTO dto = new RespuestaDTO();
        dto.setId(model.getId());
        dto.setPreguntaId(model.getPregunta().getId());
        dto.setAdopcionId(model.getAdopcion() != null ? model.getAdopcion().getId() : null);
        dto.setAcogidaId(model.getAcogida() != null ? model.getAcogida().getId() : null);
        dto.setRespuesta(model.getRespuesta());
        dto.setCreated_at(model.getCreated_at());
        return dto;
    }
}
