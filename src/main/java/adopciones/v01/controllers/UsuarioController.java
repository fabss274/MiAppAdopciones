package adopciones.v01.controllers;

import adopciones.v01.dto.usuario.UsuarioRegistroDTO;
import adopciones.v01.dto.usuario.UsuarioRespuestaDTO;
import adopciones.v01.services.usuarios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ***************** CRUD
    @GetMapping
    public List<UsuarioRespuestaDTO> getUsers() {
        return usuarioService.listarUsuarios();
    }


    @PostMapping
    public UsuarioRespuestaDTO saveUsuario(@RequestBody UsuarioRegistroDTO usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioRespuestaDTO editUsuario(@PathVariable Long id, @RequestBody UsuarioRegistroDTO usuario) {
        return usuarioService.editarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable long id) {
        if (usuarioService.eliminarUsuario(id)) {
            return ResponseEntity.ok("Usuario eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    // ***************** BUSQUEDAS

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespuestaDTO> buscarUsuarioPorId(@PathVariable long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioRespuestaDTO> buscarUsuarioPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{rol}")
    public List<UsuarioRespuestaDTO> buscarUsuarioPorRol (@PathVariable String rol) {
        return usuarioService.buscarPorRol(rol);
    }

    @GetMapping("/buscarCuentasActivas")
    public List<UsuarioRespuestaDTO> buscarCuentasActivas() {
        return usuarioService.buscarUsuariosActivos();
    }

    @GetMapping("/buscarCuentasDesactivadas")
    public List<UsuarioRespuestaDTO> buscarCuentasDesactivadas() {
        return usuarioService.buscarUsuariosDesactivados();
    }

}
