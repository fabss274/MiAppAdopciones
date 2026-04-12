package adopciones.v01.dto.perfilpersona;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PerfilPersonaDTO {
    private Long id;
    private Long idUsuario;
    private String dni;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String telefono;
    private PerfilPersonaDireccionDTO direccionCompleta;
}
