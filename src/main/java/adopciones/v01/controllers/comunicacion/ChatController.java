package adopciones.v01.controllers.comunicacion;

import adopciones.v01.dto.chat.ChatDTO;
import adopciones.v01.services.comunicacion.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChatDTO>> listarChats() {
        return ResponseEntity.ok(chatService.listarChats());
    }

    // ADOPTANTE y REFUGIO pueden crear chats
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ADOPTANTE', 'REFUGIO')")
    public ResponseEntity<?> crearChat(@RequestBody ChatDTO dto) {
        try {
            ChatDTO creado = chatService.crearChat(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarChat(@PathVariable Long id) {
        boolean eliminado = chatService.eliminarChat(id);
        if (eliminado) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat no encontrado (id: " + id + ")");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO', 'ADOPTANTE')")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return chatService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat no encontrado (id: " + id + ")"));
    }

    // Cada ADOPTANTE solo debería consultar su propio solicitanteId (validar en frontend o añadir check en service)
    @GetMapping("/solicitante/{solicitanteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADOPTANTE')")
    public ResponseEntity<?> buscarPorSolicitante(@PathVariable Long solicitanteId) {
        try {
            return ResponseEntity.ok(chatService.buscarPorSolicitante(solicitanteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/refugio/{refugioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO')")
    public ResponseEntity<?> buscarPorRefugio(@PathVariable Long refugioId) {
        try {
            return ResponseEntity.ok(chatService.buscarPorRefugio(refugioId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/animal/{animalId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REFUGIO')")
    public ResponseEntity<?> buscarPorAnimal(@PathVariable Long animalId) {
        try {
            return ResponseEntity.ok(chatService.buscarPorAnimal(animalId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
