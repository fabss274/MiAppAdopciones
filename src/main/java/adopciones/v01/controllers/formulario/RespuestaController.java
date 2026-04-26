package adopciones.v01.controllers.formulario;

import adopciones.v01.dto.respuesta.RespuestaDTO;
import adopciones.v01.services.formulario.RespuestaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
@RequiredArgsConstructor
public class RespuestaController {

    private final RespuestaService respuestaService;

    @GetMapping
    public ResponseEntity<List<RespuestaDTO>> listarRespuestas() {
        return ResponseEntity.ok(respuestaService.listarRespuestas());
    }

    @PostMapping
    public ResponseEntity<?> crearRespuesta(@RequestBody RespuestaDTO dto) {
        try {
            RespuestaDTO creada = respuestaService.crearRespuesta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarRespuesta(@PathVariable Long id, @RequestBody RespuestaDTO dto) {
        try {
            RespuestaDTO editada = respuestaService.editarRespuesta(id, dto);
            return ResponseEntity.ok(editada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRespuesta(@PathVariable Long id) {
        boolean eliminada = respuestaService.eliminarRespuesta(id);
        if (eliminada) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Respuesta no encontrada (id: " + id + ")");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return respuestaService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Respuesta no encontrada (id: " + id + ")"));
    }

    @GetMapping("/adopcion/{adopcionId}")
    public ResponseEntity<?> buscarPorAdopcion(@PathVariable Long adopcionId) {
        try {
            return ResponseEntity.ok(respuestaService.buscarPorAdopcion(adopcionId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/acogida/{acogidaId}")
    public ResponseEntity<?> buscarPorAcogida(@PathVariable Long acogidaId) {
        try {
            return ResponseEntity.ok(respuestaService.buscarPorAcogida(acogidaId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/pregunta/{preguntaId}")
    public ResponseEntity<?> buscarPorPregunta(@PathVariable Long preguntaId) {
        try {
            return ResponseEntity.ok(respuestaService.buscarPorPregunta(preguntaId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
