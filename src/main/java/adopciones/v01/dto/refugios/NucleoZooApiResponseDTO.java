package adopciones.v01.dto.refugios;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NucleoZooApiResponseDTO {

    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("results")
    private List<NucleoZooRecordDTO> results;
}
