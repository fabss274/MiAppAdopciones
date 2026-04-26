package adopciones.v01.services.comunicacion;

import adopciones.v01.dto.chat.ChatDTO;
import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.comunicacion.ChatModel;
import adopciones.v01.models.refugios.RefugioModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import adopciones.v01.repositories.adopciones.AcogidaRepository;
import adopciones.v01.repositories.adopciones.AdopcionRepository;
import adopciones.v01.repositories.animales.AnimalitoRepository;
import adopciones.v01.repositories.comunicacion.ChatRepository;
import adopciones.v01.repositories.refugios.RefugioRepository;
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
public class ChatService {

    private final ChatRepository chatRepo;
    private final AnimalitoRepository animalitoRepo;
    private final UsuarioRepository usuarioRepo;
    private final RefugioRepository refugioRepo;
    private final AdopcionRepository adopcionRepo;
    private final AcogidaRepository acogidaRepo;

    // ***************** CRUD

    @Transactional(readOnly = true)
    public List<ChatDTO> listarChats() {
        return chatRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ChatDTO crearChat(ChatDTO dto) {
        AnimalitoModel animal = animalitoRepo.findById(dto.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal no encontrado (id: " + dto.getAnimalId() + ")"));

        UsuarioModel solicitante = usuarioRepo.findById(dto.getSolicitanteId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + dto.getSolicitanteId() + ")"));

        RefugioModel refugio = refugioRepo.findById(dto.getRefugioId())
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado (id: " + dto.getRefugioId() + ")"));

        // Validar que no exista ya un chat para esa solicitud
        if (dto.getAdopcionId() != null) {
            AdopcionModel adopcion = adopcionRepo.findById(dto.getAdopcionId())
                    .orElseThrow(() -> new RuntimeException("Adopción no encontrada"));
            if (chatRepo.existsByAdopcion(adopcion)) {
                throw new RuntimeException("Ya existe un chat para esta adopción");
            }
        }
        if (dto.getAcogidaId() != null) {
            AcogidaModel acogida = acogidaRepo.findById(dto.getAcogidaId())
                    .orElseThrow(() -> new RuntimeException("Acogida no encontrada"));
            if (chatRepo.existsByAcogida(acogida)) {
                throw new RuntimeException("Ya existe un chat para esta acogida");
            }
        }

        ChatModel chat = new ChatModel();
        chat.setAnimal(animal);
        chat.setSolicitante(solicitante);
        chat.setRefugio(refugio);
        chat.setTipoSolicitudChat(dto.getTipoSolicitudChat());
        chat.setCreatedAt(LocalDateTime.now());

        if (dto.getAdopcionId() != null) {
            chat.setAdopcion(adopcionRepo.findById(dto.getAdopcionId()).orElse(null));
        }
        if (dto.getAcogidaId() != null) {
            chat.setAcogida(acogidaRepo.findById(dto.getAcogidaId()).orElse(null));
        }

        return toDTO(chatRepo.save(chat));
    }

    @Transactional
    public boolean eliminarChat(Long id) {
        if (!chatRepo.existsById(id)) return false;
        chatRepo.deleteById(id);
        return true;
    }

    // ***************** BUSQUEDAS

    @Transactional(readOnly = true)
    public Optional<ChatDTO> buscarPorId(Long id) {
        return chatRepo.findById(id).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<ChatDTO> buscarPorSolicitante(Long solicitanteId) {
        UsuarioModel usuario = usuarioRepo.findById(solicitanteId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + solicitanteId + ")"));
        return chatRepo.findBySolicitante(usuario).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatDTO> buscarPorRefugio(Long refugioId) {
        RefugioModel refugio = refugioRepo.findById(refugioId)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado (id: " + refugioId + ")"));
        return chatRepo.findByRefugio(refugio).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatDTO> buscarPorAnimal(Long animalId) {
        AnimalitoModel animal = animalitoRepo.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado (id: " + animalId + ")"));
        return chatRepo.findByAnimal(animal).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ***************** TRADUCTOR

    private ChatDTO toDTO(ChatModel model) {
        ChatDTO dto = new ChatDTO();
        dto.setId(model.getId());
        dto.setAnimalId(model.getAnimal().getId());
        dto.setSolicitanteId(model.getSolicitante().getId());
        dto.setRefugioId(model.getRefugio().getId());
        dto.setTipoSolicitudChat(model.getTipoSolicitudChat());
        dto.setAdopcionId(model.getAdopcion() != null ? model.getAdopcion().getId() : null);
        dto.setAcogidaId(model.getAcogida() != null ? model.getAcogida().getId() : null);
        dto.setCreatedAt(model.getCreatedAt());
        return dto;
    }
}
