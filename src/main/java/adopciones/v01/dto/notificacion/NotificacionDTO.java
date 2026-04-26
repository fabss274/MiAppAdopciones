package adopciones.v01.dto.notificacion;

import adopciones.v01.enums.tipoNotificacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionDTO {
    private Long id;
    private Long usuarioId;
    private tipoNotificacion tipoNotificacion;
    private String mensaje;
    private boolean leido;
    private LocalDateTime fecha;
}
