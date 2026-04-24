package adopciones.v01.dto.usuario;

import lombok.*;

@Data
@RequiredArgsConstructor
public class UsuarioRespuestaDTO {

    private Long id;
    private String email;
    private String rol;
    private boolean activo;
}
