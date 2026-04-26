package adopciones.v01.services.comunicacion;

import adopciones.v01.dto.mensaje.MensajeDTO;
import adopciones.v01.enums.tipoNotificacion;
import adopciones.v01.models.comunicacion.ChatModel;
import adopciones.v01.models.comunicacion.MensajeModel;
import adopciones.v01.models.comunicacion.NotificacionModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import adopciones.v01.repositories.comunicacion.ChatRepository;
import adopciones.v01.repositories.comunicacion.MensajeRepository;
import adopciones.v01.repositories.comunicacion.NotificacionRepository;
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
public class MensajeService {
    private final MensajeRepository mensajeRepo;
    private final ChatRepository chatRepo;
    private final UsuarioRepository usuarioRepo;
    private final NotificacionRepository notificacionRepo;

    // ***************** CRUD

    @Transactional(readOnly = true)
    public List<MensajeDTO> listarMensajes() {
        return mensajeRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public MensajeDTO enviarMensaje(MensajeDTO dto) {
        ChatModel chat = chatRepo.findById(dto.getChatId())
                .orElseThrow(() -> new RuntimeException("Chat no encontrado (id: " + dto.getChatId() + ")"));

        UsuarioModel remitente = usuarioRepo.findById(dto.getRemitenteId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + dto.getRemitenteId() + ")"));

        // El remitente debe ser participante del chat
        boolean esSolicitante = chat.getSolicitante().getId().equals(remitente.getId());
        boolean esRefugio = chat.getRefugio().getUsuario() != null &&
                chat.getRefugio().getUsuario().getId().equals(remitente.getId());

        if (!esSolicitante && !esRefugio) {
            throw new RuntimeException("El usuario no pertenece a este chat");
        }

        if (dto.getContenido() == null || dto.getContenido().isBlank()) {
            throw new RuntimeException("El contenido del mensaje no puede estar vacío");
        }

        MensajeModel mensaje = new MensajeModel();
        mensaje.setChat(chat);
        mensaje.setRemitente(remitente);
        mensaje.setContenido(dto.getContenido());
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensaje.setLeido(false);

        MensajeModel guardado = mensajeRepo.save(mensaje);

        // Notificar al otro participante
        UsuarioModel destinatario = esSolicitante
                ? chat.getRefugio().getUsuario()
                : chat.getSolicitante();

        if (destinatario != null) {
            NotificacionModel notificacion = new NotificacionModel();
            notificacion.setUsuario(destinatario);
            notificacion.setTipoNotificacion(tipoNotificacion.CHAT);
            notificacion.setMensaje("Nuevo mensaje de " + remitente.getEmail() +
                    " sobre " + chat.getAnimal().getNombre());
            notificacion.setLeido(false);
            notificacion.setFecha(LocalDateTime.now());
            notificacionRepo.save(notificacion);
        }

        return toDTO(guardado);
    }

    @Transactional
    public boolean eliminarMensaje(Long id) {
        if (!mensajeRepo.existsById(id)) return false;
        mensajeRepo.deleteById(id);
        return true;
    }

    // ***************** BUSQUEDAS

    @Transactional(readOnly = true)
    public Optional<MensajeDTO> buscarPorId(Long id) {
        return mensajeRepo.findById(id).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<MensajeDTO> buscarPorChat(Long chatId) {
        ChatModel chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado (id: " + chatId + ")"));
        return mensajeRepo.findByChatOrderByFechaEnvioAsc(chat)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public int contarNoLeidosEnChat(Long chatId, Long usuarioId) {
        ChatModel chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado (id: " + chatId + ")"));
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));
        return mensajeRepo.countByChatAndLeidoFalseAndRemitenteNot(chat, usuario);
    }

    // Marcar como leídos todos los mensajes del otro en un chat
    @Transactional
    public void marcarLeidosEnChat(Long chatId, Long miUsuarioId) {
        ChatModel chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado (id: " + chatId + ")"));
        UsuarioModel yo = usuarioRepo.findById(miUsuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + miUsuarioId + ")"));
        mensajeRepo.marcarTodosLeidosEnChat(chat, yo);
    }

    // ***************** TRADUCTOR

    private MensajeDTO toDTO(MensajeModel model) {
        MensajeDTO dto = new MensajeDTO();
        dto.setId(model.getId());
        dto.setChatId(model.getChat().getId());
        dto.setRemitenteId(model.getRemitente().getId());
        dto.setContenido(model.getContenido());
        dto.setFecha_envio(model.getFechaEnvio());
        dto.setLeido(model.isLeido());
        return dto;
    }
}
