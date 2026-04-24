package adopciones.v01.dto.pregunta;

import adopciones.v01.enums.tipoSintaxisPregunta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaDTO {
    private Long id;
    private Long formularioId;
    private String pregunta;
    private tipoSintaxisPregunta tipoSintaxisPregunta;
    private Integer orden;
    private boolean obligatoria;
}
