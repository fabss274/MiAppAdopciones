package adopciones.v01.controllers.comunicacion;

import adopciones.v01.dto.notificacion.NotificacionDTO;
import adopciones.v01.services.comunicacion.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NotificacionDTO>> listarNotificaciones() {
        return ResponseEntity.ok(notificacionService.listarNotificaciones());
    }

    // Solo ADMIN puede crear notificaciones manualmente
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crearNotificacion(@RequestBody NotificacionDTO dto) {
        try {
            NotificacionDTO creada = notificacionService.crearNotificacion(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarNotificacion(@PathVariable Long id) {
        boolean eliminada = notificacionService.eliminarNotificacion(id);
        if (eliminada) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificación no encontrada (id: " + id + ")");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return notificacionService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificación no encontrada (id: " + id + ")"));
    }

    // Cada usuario consulta sus propias notificaciones
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(notificacionService.buscarPorUsuario(usuarioId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Badge de campana: cuántas no leídas tiene el usuario
    @GetMapping("/usuario/{usuarioId}/no-leidas/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> contarNoLeidas(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(notificacionService.contarNoLeidasPorUsuario(usuarioId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> buscarNoLeidas(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(notificacionService.buscarNoLeidasPorUsuario(usuarioId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/marcar-leida")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> marcarLeida(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(notificacionService.marcarLeida(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Marcar todas de golpe (botón "marcar todo como leído")
    @PatchMapping("/usuario/{usuarioId}/marcar-todas-leidas")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> marcarTodasLeidas(@PathVariable Long usuarioId) {
        try {
            notificacionService.marcarTodasLeidas(usuarioId);
            return ResponseEntity.ok("Todas las notificaciones marcadas como leídas");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
