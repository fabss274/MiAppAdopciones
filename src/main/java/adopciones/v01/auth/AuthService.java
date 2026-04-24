package adopciones.v01.auth;

import adopciones.v01.dto.usuario.UsuarioLoginDTO;
import adopciones.v01.dto.usuario.UsuarioRegistroDTO;
import adopciones.v01.enums.rol;
import adopciones.v01.jwt.JwtService;
import adopciones.v01.models.usuarios.UsuarioModel;
import adopciones.v01.repositories.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(UsuarioLoginDTO loginDTO) {
        // Autentica con email (username en Spring Security) y contraseña
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getContrasena()
                )
        );

        UsuarioModel usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.getToken(usuario);

        return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }

    public AuthResponse register(UsuarioRegistroDTO registroDTO) {
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        UsuarioModel nuevoUsuario = new UsuarioModel();
        nuevoUsuario.setEmail(registroDTO.getEmail());
        nuevoUsuario.setContrasena(passwordEncoder.encode(registroDTO.getContrasena()));
        nuevoUsuario.setRol(rol.valueOf(registroDTO.getRol().toUpperCase()));
        nuevoUsuario.setActivo(true);

        usuarioRepository.save(nuevoUsuario);

        String token = jwtService.getToken(nuevoUsuario);

        return AuthResponse.builder()
                .token(token)
                .email(nuevoUsuario.getEmail())
                .rol(nuevoUsuario.getRol().name())
                .build();
    }
}
