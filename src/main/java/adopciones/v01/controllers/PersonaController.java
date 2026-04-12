package adopciones.v01.controllers;

import adopciones.v01.dto.perfilpersona.PerfilPersonaDTO;
import adopciones.v01.services.usuarios.PersonaService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @GetMapping
    public List<PerfilPersonaDTO> getPersonas() {
        return personaService.listarPersonas();
    }



    @PostMapping
    public PerfilPersonaDTO savePersona(@RequestBody PerfilPersonaDTO persona) {
        return personaService.crearPerfilPersona(persona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilPersonaDTO> editPersona(@PathVariable Long id, @RequestBody PerfilPersonaDTO persona) {
        return ResponseEntity.ok(personaService.editarPerfilPersona(id, persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable Long id) {
        if (personaService.eliminarPerfilPersona(id)) {
            return ResponseEntity.ok("Perfil eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilPersonaDTO> buscarPersonaPorId(@PathVariable Long id) {
        return personaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<PerfilPersonaDTO> buscarPersonaPorDni(@PathVariable String dni) {
        return personaService.buscarPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<PerfilPersonaDTO> buscarPersonaPorTelefono(@PathVariable String telefono) {
        return personaService.buscarPorTelefono(telefono)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codpostal/{codPostal}")
    public List<PerfilPersonaDTO> buscarPorCodPostal(@PathVariable String codPostal) {
        return personaService.buscarPorCodPostal(codPostal);
    }

    @GetMapping("/localidad/{localidad}")
    public List<PerfilPersonaDTO> buscarPorLocalidad(@PathVariable String localidad) {
        return personaService.buscarPorLocalidad(localidad);
    }
}
