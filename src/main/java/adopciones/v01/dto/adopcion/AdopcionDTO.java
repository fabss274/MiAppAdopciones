package adopciones.v01.dto.adopcion;

import adopciones.v01.enums.estadoAdopcion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdopcionDTO {
    private Long id;
    private Long animalId;
    private Long usuarioId;
    private estadoAdopcion estadoAdopcion;
    private String motivo_rechazo;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
