package adopciones.v01.controllers.formulario;

import adopciones.v01.dto.formulario.FormularioDTO;
import adopciones.v01.enums.tipoFormulario;
import adopciones.v01.services.formulario.FormularioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formularios")
@RequiredArgsConstructor
public class FormularioController {
    private final FormularioService formularioService;

    // ***************** CRUD

    @GetMapping
    public ResponseEntity<List<FormularioDTO>> listarFormularios() {
        return ResponseEntity.ok(formularioService.listarFormularios());
    }

    @PostMapping
    public ResponseEntity<?> crearFormulario(@RequestBody FormularioDTO dto) {
        try {
            FormularioDTO creado = formularioService.crearFormulario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarFormulario(@PathVariable Long id, @RequestBody FormularioDTO dto) {
        try {
            FormularioDTO editado = formularioService.editarFormulario(id, dto);
            return ResponseEntity.ok(editado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFormulario(@PathVariable Long id) {
        boolean eliminado = formularioService.eliminarFormulario(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Formulario no encontrado (id: " + id + ")");
    }

    // PATCH limpio solo para activar/desactivar
    @PatchMapping("/{id}/toggle-activo")
    public ResponseEntity<?> toggleActivo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(formularioService.toggleActivo(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // ***************** BUSQUEDAS

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return formularioService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Formulario no encontrado (id: " + id + ")"));
    }

    @GetMapping("/refugio/{refugioId}")
    public ResponseEntity<?> buscarPorRefugio(@PathVariable Long refugioId) {
        try {
            return ResponseEntity.ok(formularioService.buscarPorRefugio(refugioId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<FormularioDTO>> buscarPorTipo(@PathVariable tipoFormulario tipo) {
        return ResponseEntity.ok(formularioService.buscarPorTipo(tipo));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<FormularioDTO>> buscarActivos() {
        return ResponseEntity.ok(formularioService.buscarActivos());
    }

    @GetMapping("/refugio/{refugioId}/filtro")
    public ResponseEntity<?> buscarPorRefugioYActivo(
            @PathVariable Long refugioId,
            @RequestParam boolean activo) {
        try {
            return ResponseEntity.ok(formularioService.buscarPorRefugioYActivo(refugioId, activo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
