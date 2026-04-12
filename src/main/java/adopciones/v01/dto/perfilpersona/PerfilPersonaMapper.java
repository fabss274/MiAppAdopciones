package adopciones.v01.dto.perfilpersona;

import adopciones.v01.models.usuarios.PersonaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerfilPersonaMapper {

    @Mapping(target = "idUsuario", source = "usuario.id")
    @Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
    PerfilPersonaImportanteDTO toPerfilPersonaImportanteDTO(PersonaModel persona);

    @Mapping(target = "direccion", source = "direccion")
    @Mapping(target = "localidad", source = "localidad")
    @Mapping(target = "provincia", source = "provincia")
    @Mapping(target = "codPostal", source = "codpostal")
    PerfilPersonaDireccionDTO toPerfilPersonaDireccionDTO(PersonaModel persona);

    @Mapping(target = "idUsuario", source = "usuario.id")
    @Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
    PerfilPersonaDTO toPerfilPersonaDTO(PersonaModel persona);

    List<PerfilPersonaImportanteDTO> toPerfilPersonaImportanteDTO(List<PersonaModel> listaPersonas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "direccionCompleta.direccion", target = "direccion")
    @Mapping(source = "direccionCompleta.localidad", target = "localidad")
    @Mapping(source = "direccionCompleta.provincia", target = "provincia")
    @Mapping(source = "direccionCompleta.codPostal", target = "codpostal")
    PersonaModel toPersonaModel(PerfilPersonaDTO perfilPersonaDTO);
}
