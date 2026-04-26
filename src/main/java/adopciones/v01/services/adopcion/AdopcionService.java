package adopciones.v01.services.adopcion;

import adopciones.v01.dto.adopcion.AdopcionDTO;
import adopciones.v01.enums.estadoAdopcion;
import adopciones.v01.enums.estadoAnimal;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import adopciones.v01.repositories.adopciones.AdopcionRepository;
import adopciones.v01.repositories.animales.AnimalitoRepository;
import adopciones.v01.repositories.usuarios.UsuarioRepository;
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
public class AdopcionService {

    private final AdopcionRepository adopcionRepo;
    private final AnimalitoRepository animalitoRepo;
    private final UsuarioRepository usuarioRepo;

    // ***************** CRUD

    @Transactional(readOnly = true)
    public List<AdopcionDTO> listarAdopciones() {
        return adopcionRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AdopcionDTO crearAdopcion(AdopcionDTO dto) {
        AnimalitoModel animal = animalitoRepo.findById(dto.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal no encontrado (id: " + dto.getAnimalId() + ")"));

        UsuarioModel usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + dto.getUsuarioId() + ")"));

        if (animal.getEstadoAnimal() != estadoAnimal.DISPONIBLE) {
            throw new RuntimeException("El animal no está disponible para adopción (estado: " + animal.getEstadoAnimal() + ")");
        }

        // Comprobar que no tenga ya una solicitud activa para este animal
        if (adopcionRepo.existsByUsuarioAndAnimalAndEstadoAdopcionNot(usuario, animal, estadoAdopcion.RECHAZADA)) {
            throw new RuntimeException("El usuario ya tiene una solicitud de adopción activa para este animal");
        }

        AdopcionModel adopcion = new AdopcionModel();
        adopcion.setAnimal(animal);
        adopcion.setUsuario(usuario);
        adopcion.setEstadoAdopcion(estadoAdopcion.ENVIADA);
        adopcion.setCreatedAt(LocalDateTime.now());
        adopcion.setUpdatedAt(LocalDateTime.now());

        return toDTO(adopcionRepo.save(adopcion));
    }

    @Transactional
    public AdopcionDTO cambiarEstado(Long id, estadoAdopcion nuevoEstado, String motivoRechazo) {
        AdopcionModel adopcion = adopcionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Adopción no encontrada (id: " + id + ")"));

        validarTransicionEstado(adopcion.getEstadoAdopcion(), nuevoEstado);

        if (nuevoEstado == estadoAdopcion.RECHAZADA) {
            if (motivoRechazo == null || motivoRechazo.isBlank()) {
                throw new RuntimeException("El motivo de rechazo es obligatorio al rechazar una adopción");
            }
            adopcion.setMotivoRechazo(motivoRechazo);
        }

        // Al aprobar, actualizar el estado del animal
        if (nuevoEstado == estadoAdopcion.APROBADA) {
            AnimalitoModel animal = adopcion.getAnimal();
            animal.setEstadoAnimal(estadoAnimal.ADOPTADO);
            animal.setUpdated_at(LocalDateTime.now());
            animalitoRepo.save(animal);
        }

        adopcion.setEstadoAdopcion(nuevoEstado);
        adopcion.setUpdatedAt(LocalDateTime.now());

        return toDTO(adopcionRepo.save(adopcion));
    }

    @Transactional
    public boolean eliminarAdopcion(Long id) {
        if (!adopcionRepo.existsById(id)) {
            return false;
        }
        adopcionRepo.deleteById(id);
        return true;
    }

    // ***************** BUSQUEDAS

    @Transactional(readOnly = true)
    public Optional<AdopcionDTO> buscarPorId(Long id) {
        return adopcionRepo.findById(id).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<AdopcionDTO> buscarPorUsuario(Long usuarioId) {
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));
        return adopcionRepo.findByUsuario(usuario)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdopcionDTO> buscarPorAnimal(Long animalId) {
        AnimalitoModel animal = animalitoRepo.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado (id: " + animalId + ")"));
        return adopcionRepo.findByAnimal(animal)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdopcionDTO> buscarPorEstado(estadoAdopcion estado) {
        return adopcionRepo.findByEstadoAdopcion(estado)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdopcionDTO> buscarPorUsuarioYEstado(Long usuarioId, estadoAdopcion estado) {
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));
        return adopcionRepo.findByUsuarioAndEstadoAdopcion(usuario, estado)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ***************** VALIDACION ESTADO

    private void validarTransicionEstado(estadoAdopcion actual, estadoAdopcion nuevo) {
        boolean valida = switch (actual) {
            case ENVIADA      -> nuevo == estadoAdopcion.EN_REVISION || nuevo == estadoAdopcion.RECHAZADA;
            case EN_REVISION  -> nuevo == estadoAdopcion.VISITA_PRE  || nuevo == estadoAdopcion.RECHAZADA;
            case VISITA_PRE   -> nuevo == estadoAdopcion.APROBADA    || nuevo == estadoAdopcion.RECHAZADA;
            case APROBADA, RECHAZADA -> false; // estados finales
        };

        if (!valida) {
            throw new RuntimeException("Transición de estado no permitida: " + actual + " → " + nuevo);
        }
    }

    // ***************** TRADUCTOR

    private AdopcionDTO toDTO(AdopcionModel model) {
        AdopcionDTO dto = new AdopcionDTO();
        dto.setId(model.getId());
        dto.setAnimalId(model.getAnimal().getId());
        dto.setUsuarioId(model.getUsuario().getId());
        dto.setEstadoAdopcion(model.getEstadoAdopcion());
        dto.setMotivo_rechazo(model.getMotivoRechazo());
        dto.setCreated_at(model.getCreatedAt());
        dto.setUpdated_at(model.getUpdatedAt());
        return dto;
    }
}
