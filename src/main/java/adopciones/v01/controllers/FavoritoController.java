package adopciones.v01.controllers;

import adopciones.v01.dto.favorito.FavoritoDTO;
import adopciones.v01.services.animales.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    // ***************** CRUD

    @GetMapping
    public ResponseEntity<List<FavoritoDTO>> listarFavoritos() {
        return ResponseEntity.ok(favoritoService.listarFavoritos());
    }

    @PostMapping
    public ResponseEntity<?> agregarFavorito(@RequestBody FavoritoDTO dto) {
        try {
            FavoritoDTO creado = favoritoService.agregarFavorito(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFavorito(@PathVariable Long id) {
        boolean eliminado = favoritoService.eliminarFavorito(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorito no encontrado (id: " + id + ")");
    }

    // DELETE limpio por usuario+animal (útil para el botón de "quitar de favoritos")
    @DeleteMapping("/usuario/{usuarioId}/animal/{animalId}")
    public ResponseEntity<?> eliminarFavoritoPorUsuarioYAnimal(
            @PathVariable Long usuarioId,
            @PathVariable Long animalId) {
        try {
            boolean eliminado = favoritoService.eliminarFavoritoPorUsuarioYAnimal(usuarioId, animalId);
            if (eliminado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorito no encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ***************** BUSQUEDAS

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return favoritoService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorito no encontrado (id: " + id + ")"));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(favoritoService.buscarPorUsuario(usuarioId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<?> buscarPorAnimal(@PathVariable Long animalId) {
        try {
            return ResponseEntity.ok(favoritoService.buscarPorAnimal(animalId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Útil para saber si pintar el corazón relleno o vacío en el frontend
    @GetMapping("/existe")
    public ResponseEntity<?> existeFavorito(
            @RequestParam Long usuarioId,
            @RequestParam Long animalId) {
        try {
            boolean existe = favoritoService.existeFavorito(usuarioId, animalId);
            return ResponseEntity.ok(existe);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
