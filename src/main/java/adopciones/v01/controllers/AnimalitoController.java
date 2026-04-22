package adopciones.v01.controllers;

import adopciones.v01.enums.estadoAnimal;
import adopciones.v01.dto.animales.AnimalitoDTO;
import adopciones.v01.services.animales.AnimalitosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animales")
@RequiredArgsConstructor
public class AnimalitoController {

    private final AnimalitosService animalitoService;

    // ***************** CRUD

    @GetMapping
    public ResponseEntity<List<AnimalitoDTO>> listarAnimales() {
        return ResponseEntity.ok(animalitoService.listarAnimales());
    }

    @PostMapping
    public ResponseEntity<?> crearAnimalito(@RequestBody AnimalitoDTO dto) {
        try {
            AnimalitoDTO creado = animalitoService.crearAnimalito(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarAnimalito(@PathVariable Long id, @RequestBody AnimalitoDTO dto) {
        try {
            AnimalitoDTO editado = animalitoService.editarAnimalito(id, dto);
            return ResponseEntity.ok(editado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAnimalito(@PathVariable Long id) {
        boolean eliminado = animalitoService.eliminarAnimalito(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal no encontrado (id: " + id + ")");
    }

    // ***************** BÚSQUEDAS

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return animalitoService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal no encontrado (id: " + id + ")"));
    }

    @GetMapping("/chip/{chip}")
    public ResponseEntity<?> buscarPorChip(@PathVariable String chip) {
        return animalitoService.buscarPorChip(chip)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal no encontrado con chip: " + chip));
    }

    @GetMapping("/ficha/{numFicha}")
    public ResponseEntity<?> buscarPorNumFicha(@PathVariable String numFicha) {
        return animalitoService.buscarPorNumFicha(numFicha)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal no encontrado con ficha: " + numFicha));
    }

    @GetMapping("/refugio/{refugioId}")
    public ResponseEntity<List<AnimalitoDTO>> buscarPorRefugio(@PathVariable Long refugioId) {
        return ResponseEntity.ok(animalitoService.buscarPorRefugio(refugioId));
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<AnimalitoDTO>> buscarPorEspecie(@PathVariable String especie) {
        return ResponseEntity.ok(animalitoService.buscarPorEspecie(especie));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AnimalitoDTO>> buscarPorEstado(@PathVariable estadoAnimal estado) {
        return ResponseEntity.ok(animalitoService.buscarPorEstado(estado));
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<AnimalitoDTO>> buscarPorEspecieYEstado(
            @RequestParam String especie,
            @RequestParam estadoAnimal estado) {
        return ResponseEntity.ok(animalitoService.buscarPorEspecieYEstado(especie, estado));
    }
}
