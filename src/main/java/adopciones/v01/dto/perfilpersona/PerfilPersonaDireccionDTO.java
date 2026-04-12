package adopciones.v01.dto.perfilpersona;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PerfilPersonaDireccionDTO {
    private String direccion;
    private String localidad;
    private String provincia;
    private String codPostal;
}
