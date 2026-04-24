package adopciones.v01.auth;

import adopciones.v01.dto.usuario.UsuarioLoginDTO;
import adopciones.v01.dto.usuario.UsuarioRegistroDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UsuarioLoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UsuarioRegistroDTO registroDTO) {
        return ResponseEntity.ok(authService.register(registroDTO));
    }
}
