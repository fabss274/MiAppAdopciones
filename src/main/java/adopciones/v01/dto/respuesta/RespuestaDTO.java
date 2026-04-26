package adopciones.v01.dto.respuesta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaDTO {
    private Long id;
    private Long preguntaId;
    private Long adopcionId;
    private Long acogidaId;
    private String respuesta;
    private LocalDateTime created_at;
}
