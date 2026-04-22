package adopciones.v01.controllers;

import adopciones.v01.dto.refugio.RefugioDTO;
import adopciones.v01.services.refugios.RefugioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/ApiRefugio")
@RequiredArgsConstructor
public class RefugioController {

    private final RefugioService refugioService;

    // ***************** CRUD
    @GetMapping
    public List<RefugioDTO> getRefugios() {
        return refugioService.listarRefugios();
    }

    @PostMapping
    public ResponseEntity<RefugioDTO> saveRefugio(@RequestBody RefugioDTO refugioDTO) {
        try {
            return ResponseEntity.ok(refugioService.crearRefugio(refugioDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefugioDTO> editRefugio(@PathVariable Long id, @RequestBody RefugioDTO refugioDTO) {
        try {
            return ResponseEntity.ok(refugioService.editarRefugio(id, refugioDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRefugio(@PathVariable Long id) {
        if (refugioService.eliminarRefugio(id)) {
            return ResponseEntity.ok("Refugio eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }


    // ***************** BUSQUEDAS
    @GetMapping("/{id}")
    public ResponseEntity<RefugioDTO> buscarPorId(@PathVariable Long id) {
        return refugioService.listarRefugios().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping("/nucleo/{nucleo}")
//    public ResponseEntity<RefugioDTO> buscarPorNucleo(@PathVariable String nucleo) {
//        return refugioService.buscarPorNucleoZoologico(nucleo)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

}
