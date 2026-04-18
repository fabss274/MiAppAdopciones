package adopciones.v01.dto.refugios;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NucleoZooRecordDTO {

    @JsonProperty("prov_exp")
    private String provExp;

    @JsonProperty("muni_exp")
    private String muniExp;

    @JsonProperty("codigo_de_nucleo_zoologico")
    private String codigoNucleoZoologico;

    @JsonProperty("nombre_titular_nucleo_zoologico")
    private String nombreTitularNucleoZoologico;

    @JsonProperty("especie_grupo_de_especies")
    private String especieGrupoDeEspecies;

    @JsonProperty("fecha")
    private LocalDate fecha;
}
