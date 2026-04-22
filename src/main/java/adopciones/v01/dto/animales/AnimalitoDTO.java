package adopciones.v01.dto.animales;

import adopciones.v01.enums.estadoAnimal;
import adopciones.v01.enums.sexo;
import adopciones.v01.enums.tamano;
import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.animales.FavoritoModel;
import adopciones.v01.models.animales.FotoAnimalModel;
import adopciones.v01.models.refugios.RefugioModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AnimalitoDTO {
    private Long id;

    private RefugioModel refugio;

    private String chip;

    private String numFicha;

    private String nombre;


    private String especie;

    private String raza;


    private sexo sexo; // Enum: MACHO, HEMBRA


    private tamano tamano; // Enum: PEQUENO, MEDIANO, GRANDE

    private Integer edad;

    private String descripcion;


    private boolean esterilizado;


    private estadoAnimal estadoAnimal; // Enum: DISPONIBLE, RESERVADO, ADOPTADO, MUERTO

    private LocalDateTime created_at;


    private LocalDateTime updated_at;


    private List<FotoAnimalModel> fotos;


    private List<FavoritoModel> favoritos;


    private List<AdopcionModel> adopciones;


    private List<AcogidaModel> acogidas;
}
