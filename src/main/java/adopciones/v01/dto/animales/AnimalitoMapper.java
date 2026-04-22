package adopciones.v01.dto.animales;

import adopciones.v01.models.animales.AnimalitoModel;
import org.springframework.stereotype.Component;

@Component
public class AnimalitoMapper {
    public AnimalitoDTO toAnimalitoDTO(AnimalitoModel model) {
        if (model == null) return null;

        return AnimalitoDTO.builder()
                .id(model.getId())
                .refugio(model.getRefugio())
                .chip(model.getChip())
                .numFicha(model.getNumFicha())
                .nombre(model.getNombre())
                .especie(model.getEspecie())
                .raza(model.getRaza())
                .sexo(model.getSexo())
                .tamano(model.getTamano())
                .edad(model.getEdad())
                .descripcion(model.getDescripcion())
                .esterilizado(model.isEsterilizado())
                .estadoAnimal(model.getEstadoAnimal())
                .created_at(model.getCreated_at())
                .updated_at(model.getUpdated_at())
                .fotos(model.getFotos())
                .favoritos(model.getFavoritos())
                .adopciones(model.getAdopciones())
                .acogidas(model.getAcogidas())
                .build();
    }

    public AnimalitoModel toAnimalitoModel(AnimalitoDTO dto) {
        if (dto == null) return null;

        AnimalitoModel model = new AnimalitoModel();
        model.setChip(dto.getChip());
        model.setNumFicha(dto.getNumFicha());
        model.setNombre(dto.getNombre());
        model.setEspecie(dto.getEspecie());
        model.setRaza(dto.getRaza());
        model.setSexo(dto.getSexo());
        model.setTamano(dto.getTamano());
        model.setEdad(dto.getEdad());
        model.setDescripcion(dto.getDescripcion());
        model.setEsterilizado(dto.isEsterilizado());
        model.setEstadoAnimal(dto.getEstadoAnimal());
        return model;
    }
}
