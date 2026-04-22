package adopciones.v01.dto.refugio;

import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.formularios.FormularioModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RefugioDTO {
    private Long id;
    private UsuarioModel usuario;
    private String nombreRefugio;
    private String nucleoZoologico;
    private String direccion;
    private String descripcion;
    private String telefono;
    private boolean verificado;
    private List<AnimalitoModel> animales;
    private List<FormularioModel> formularios;
}
