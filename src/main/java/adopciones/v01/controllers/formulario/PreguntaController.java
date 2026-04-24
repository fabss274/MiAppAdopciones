package adopciones.v01.controllers.formulario;

import adopciones.v01.dto.pregunta.PreguntaDTO;
import adopciones.v01.enums.tipoSintaxisPregunta;
import adopciones.v01.services.formulario.PreguntaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
public class PreguntaController {

    private final PreguntaService preguntaService;

    // ***************** CRUD

    @GetMapping
    public ResponseEntity<List<PreguntaDTO>> listarPreguntas() {
        return ResponseEntity.ok(preguntaService.listarPreguntas());
    }

    @PostMapping
    public ResponseEntity<?> crearPregunta(@RequestBody PreguntaDTO dto) {
        try {
            PreguntaDTO creada = preguntaService.crearPregunta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarPregunta(@PathVariable Long id, @RequestBody PreguntaDTO dto) {
        try {
            PreguntaDTO editada = preguntaService.editarPregunta(id, dto);
            return ResponseEntity.ok(editada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPregunta(@PathVariable Long id) {
        boolean eliminada = preguntaService.eliminarPregunta(id);
        if (eliminada) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pregunta no encontrada (id: " + id + ")");
    }

    // ***************** BUSQUEDAS

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return preguntaService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pregunta no encontrada (id: " + id + ")"));
    }

    @GetMapping("/formulario/{formularioId}")
    public ResponseEntity<?> buscarPorFormulario(@PathVariable Long formularioId) {
        try {
            return ResponseEntity.ok(preguntaService.buscarPorFormulario(formularioId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/formulario/{formularioId}/filtro")
    public ResponseEntity<?> buscarPorFormularioYObligatoria(
            @PathVariable Long formularioId,
            @RequestParam boolean obligatoria) {
        try {
            return ResponseEntity.ok(preguntaService.buscarPorFormularioYObligatoria(formularioId, obligatoria));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PreguntaDTO>> buscarPorTipo(@PathVariable tipoSintaxisPregunta tipo) {
        return ResponseEntity.ok(preguntaService.buscarPorTipo(tipo));
    }
}
