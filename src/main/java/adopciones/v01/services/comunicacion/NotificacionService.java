package adopciones.v01.services.comunicacion;

import adopciones.v01.dto.notificacion.NotificacionDTO;
import adopciones.v01.models.comunicacion.NotificacionModel;
import adopciones.v01.models.usuarios.UsuarioModel;
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
@Transactional
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepo;
    private final UsuarioRepository usuarioRepo;

    // ***************** CRUD

    @Transactional(readOnly = true)
    public List<NotificacionDTO> listarNotificaciones() {
        return notificacionRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Uso interno del sistema (también expuesto para ADMIN)
    @Transactional
    public NotificacionDTO crearNotificacion(NotificacionDTO dto) {
        UsuarioModel usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + dto.getUsuarioId() + ")"));

        NotificacionModel notificacion = new NotificacionModel();
        notificacion.setUsuario(usuario);
        notificacion.setTipoNotificacion(dto.getTipoNotificacion());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setLeido(false);
        notificacion.setFecha(LocalDateTime.now());

        return toDTO(notificacionRepo.save(notificacion));
    }

    @Transactional
    public boolean eliminarNotificacion(Long id) {
        if (!notificacionRepo.existsById(id)) return false;
        notificacionRepo.deleteById(id);
        return true;
    }

    // ***************** BUSQUEDAS

    @Transactional(readOnly = true)
    public Optional<NotificacionDTO> buscarPorId(Long id) {
        return notificacionRepo.findById(id).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<NotificacionDTO> buscarPorUsuario(Long usuarioId) {
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));
        return notificacionRepo.findByUsuarioOrderByFechaDesc(usuario)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotificacionDTO> buscarNoLeidasPorUsuario(Long usuarioId) {
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));
        return notificacionRepo.findByUsuarioAndLeido(usuario, false)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public int contarNoLeidasPorUsuario(Long usuarioId) {
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));
        return notificacionRepo.countByUsuarioAndLeidoFalse(usuario);
    }

    @Transactional
    public NotificacionDTO marcarLeida(Long id) {
        NotificacionModel notificacion = notificacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada (id: " + id + ")"));
        notificacion.setLeido(true);
        return toDTO(notificacionRepo.save(notificacion));
    }

    @Transactional
    public void marcarTodasLeidas(Long usuarioId) {
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));
        notificacionRepo.marcarTodasLeidasPorUsuario(usuario);
    }

    // ***************** TRADUCTOR

    private NotificacionDTO toDTO(NotificacionModel model) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(model.getId());
        dto.setUsuarioId(model.getUsuario().getId());
        dto.setTipoNotificacion(model.getTipoNotificacion());
        dto.setMensaje(model.getMensaje());
        dto.setLeido(model.isLeido());
        dto.setFecha(model.getFecha());
        return dto;
    }
}
