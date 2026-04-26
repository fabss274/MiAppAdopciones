package adopciones.v01.controllers.comunicacion;

import adopciones.v01.dto.mensaje.MensajeDTO;
import adopciones.v01.services.comunicacion.MensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mensajes")
public class MensajeController {
    private final MensajeService mensajeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MensajeDTO>> listarMensajes() {
        return ResponseEntity.ok(mensajeService.listarMensajes());
    }

    // Cualquier participante puede enviar mensajes
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ADOPTANTE', 'REFUGIO')")
    public ResponseEntity<?> enviarMensaje(@RequestBody MensajeDTO dto) {
        try {
            MensajeDTO enviado = mensajeService.enviarMensaje(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(enviado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarMensaje(@PathVariable Long id) {
        boolean eliminado = mensajeService.eliminarMensaje(id);
        if (eliminado) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mensaje no encontrado (id: " + id + ")");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return mensajeService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mensaje no encontrado (id: " + id + ")"));
    }

    // Obtener conversación completa ordenada cronológicamente
    @GetMapping("/chat/{chatId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> buscarPorChat(@PathVariable Long chatId) {
        try {
            return ResponseEntity.ok(mensajeService.buscarPorChat(chatId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Contar mensajes no leídos (para el badge de notificaciones del chat)
    @GetMapping("/chat/{chatId}/no-leidos")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> contarNoLeidos(
            @PathVariable Long chatId,
            @RequestParam Long usuarioId) {
        try {
            return ResponseEntity.ok(mensajeService.contarNoLeidosEnChat(chatId, usuarioId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Marcar todos los mensajes de un chat como leídos al abrir la conversación
    @PatchMapping("/chat/{chatId}/marcar-leidos")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> marcarLeidosEnChat(
            @PathVariable Long chatId,
            @RequestParam Long usuarioId) {
        try {
            mensajeService.marcarLeidosEnChat(chatId, usuarioId);
            return ResponseEntity.ok("Mensajes marcados como leídos");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
