package adopciones.v01.jwt;

import lombok.Data;

@Data
public class JwtResponseDTO {
    private String token;
    private String rol;
    private String email;
}
