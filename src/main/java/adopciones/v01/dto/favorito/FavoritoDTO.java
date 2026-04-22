package adopciones.v01.dto.favorito;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoDTO {
    private Long id;
    private Long usuarioId;
    private Long animalId;
    private LocalDateTime created_at;
}
