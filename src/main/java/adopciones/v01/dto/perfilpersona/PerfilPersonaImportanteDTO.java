package adopciones.v01.dto.perfilpersona;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PerfilPersonaImportanteDTO {
    private Long id;
    private Long idUsuario;
    private String dni;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String telefono;
}
