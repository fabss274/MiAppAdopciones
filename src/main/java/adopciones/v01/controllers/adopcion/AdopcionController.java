package adopciones.v01.controllers.adopcion;

import adopciones.v01.dto.adopcion.AdopcionDTO;
import adopciones.v01.enums.estadoAdopcion;
import adopciones.v01.services.adopcion.AdopcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adopciones")
@RequiredArgsConstructor
public class AdopcionController {

    private final AdopcionService adopcionService;

    // ***************** CRUD

    @GetMapping
    public ResponseEntity<List<AdopcionDTO>> listarAdopciones() {
        return ResponseEntity.ok(adopcionService.listarAdopciones());
    }

    @PostMapping
    public ResponseEntity<?> crearAdopcion(@RequestBody AdopcionDTO dto) {
        try {
            AdopcionDTO creada = adopcionService.crearAdopcion(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // PATCH para cambiar solo el estado (con motivo opcional en body)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long id,
            @RequestParam estadoAdopcion nuevoEstado,
            @RequestParam(required = false) String motivoRechazo) {
        try {
            AdopcionDTO actualizada = adopcionService.cambiarEstado(id, nuevoEstado, motivoRechazo);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAdopcion(@PathVariable Long id) {
        boolean eliminada = adopcionService.eliminarAdopcion(id);
        if (eliminada) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopción no encontrada (id: " + id + ")");
    }

    // ***************** BUSQUEDAS

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return adopcionService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopción no encontrada (id: " + id + ")"));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(adopcionService.buscarPorUsuario(usuarioId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<?> buscarPorAnimal(@PathVariable Long animalId) {
        try {
            return ResponseEntity.ok(adopcionService.buscarPorAnimal(animalId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AdopcionDTO>> buscarPorEstado(@PathVariable estadoAdopcion estado) {
        return ResponseEntity.ok(adopcionService.buscarPorEstado(estado));
    }

    @GetMapping("/usuario/{usuarioId}/filtro")
    public ResponseEntity<?> buscarPorUsuarioYEstado(
            @PathVariable Long usuarioId,
            @RequestParam estadoAdopcion estado) {
        try {
            return ResponseEntity.ok(adopcionService.buscarPorUsuarioYEstado(usuarioId, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

