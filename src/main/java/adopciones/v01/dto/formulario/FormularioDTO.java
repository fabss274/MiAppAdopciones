package adopciones.v01.dto.formulario;

import adopciones.v01.enums.tipoFormulario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormularioDTO {
    private Long id;
    private tipoFormulario tipoFormulario;
    private Long refugioId;
    private boolean activo;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
