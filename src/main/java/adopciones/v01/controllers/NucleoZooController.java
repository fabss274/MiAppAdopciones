package adopciones.v01.controllers;

import adopciones.v01.services.NucleoZooSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/nucleos-zoologicos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NucleoZooController {

    private final NucleoZooSyncService syncService;

    @PostMapping("/sync")
    public ResponseEntity<Map<String, Object>> sincronizarNucleos(
            @RequestParam(defaultValue = "LEON") String provincia) {
        try {
            int insertados = syncService.syncByProvincia(provincia);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "provincia", provincia,
                    "registrosInsertados", insertados,
                    "mensaje", "Sincronizacion completada exitosamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @GetMapping("/verificar/{codigo}")
    public ResponseEntity<Map<String, Object>> verificarNucleo(@PathVariable String codigo) {
        try {
            boolean existe = syncService.verificarExistenciaCodigo(codigo);
            return ResponseEntity.ok(Map.of(
                    "codigo", codigo,
                    "existe", existe,
                    "mensaje", existe ? "nucleo zoologico encontrado" : "nucleo zoologico no encontrado"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}
