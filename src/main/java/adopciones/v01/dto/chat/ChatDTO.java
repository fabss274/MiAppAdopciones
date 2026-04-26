package adopciones.v01.dto.chat;

import adopciones.v01.enums.tipoSolicitudChat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDTO {
    private Long id;
    private Long animalId;
    private Long solicitanteId;
    private Long refugioId;
    private tipoSolicitudChat tipoSolicitudChat;
    private Long adopcionId;
    private Long acogidaId;
    private LocalDateTime createdAt;
}
