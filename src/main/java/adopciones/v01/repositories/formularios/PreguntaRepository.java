package adopciones.v01.repositories.formularios;

import adopciones.v01.enums.tipoSintaxisPregunta;
import adopciones.v01.models.formularios.FormularioModel;
import adopciones.v01.models.formularios.PreguntaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<PreguntaModel, Long> {

    List<PreguntaModel> findByFormulario(FormularioModel formulario);
    List<PreguntaModel> findByFormularioOrderByOrdenAsc(FormularioModel formulario);
    List<PreguntaModel> findByFormularioAndObligatoria(FormularioModel formulario, boolean obligatoria);
    List<PreguntaModel> findByTipoSintaxisPregunta(tipoSintaxisPregunta tipo);

    boolean existsByFormularioAndOrden(FormularioModel formulario, Integer orden);

    // para validar orden en edicion (excluir el propio registro)
    boolean existsByFormularioAndOrdenAndIdNot(FormularioModel formulario, Integer orden, Long id);

    // max orden actual en un formulario (para autoasignar orden al final)
    @Query("SELECT COALESCE(MAX(p.orden), 0) FROM PreguntaModel p WHERE p.formulario = :formulario")
    Integer findMaxOrdenByFormulario(@Param("formulario") FormularioModel formulario);
}
