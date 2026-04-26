package adopciones.v01.dto.mensaje;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensajeDTO {
    private Long id;
    private Long chatId;
    private Long remitenteId;
    private String contenido;
    private LocalDateTime fecha_envio;
    private boolean leido;
}
