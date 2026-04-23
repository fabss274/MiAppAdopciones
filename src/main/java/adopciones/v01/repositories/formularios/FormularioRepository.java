package adopciones.v01.repositories.formularios;

import adopciones.v01.enums.tipoFormulario;
import adopciones.v01.models.formularios.FormularioModel;
import adopciones.v01.models.refugios.RefugioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormularioRepository extends JpaRepository<FormularioModel, Long> {
    List<FormularioModel> findByRefugio(RefugioModel refugio);
    List<FormularioModel> findByTipoFormulario(tipoFormulario tipo);
    List<FormularioModel> findByActivo(boolean activo);

    List<FormularioModel> findByRefugioAndActivo(RefugioModel refugio, boolean activo);
    List<FormularioModel> findByRefugioAndTipoFormulario(RefugioModel refugio, tipoFormulario tipo);

    boolean existsByRefugioAndTipoFormularioAndActivo(
            RefugioModel refugio, tipoFormulario tipo, boolean activo
    );

    Optional<FormularioModel> findByRefugioAndTipoFormularioAndActivo(
            RefugioModel refugio, tipoFormulario tipo, boolean activo
    );
}
