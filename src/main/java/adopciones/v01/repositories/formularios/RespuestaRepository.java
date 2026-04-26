package adopciones.v01.repositories.formularios;

import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.formularios.PreguntaModel;
import adopciones.v01.models.formularios.RespuestaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<RespuestaModel, Long> {
    List<RespuestaModel> findByPregunta(PreguntaModel pregunta);
    List<RespuestaModel> findByAdopcion(AdopcionModel adopcion);
    List<RespuestaModel> findByAcogida(AcogidaModel acogida);

    boolean existsByAdopcionAndPregunta(AdopcionModel adopcion, PreguntaModel pregunta);
    boolean existsByAcogidaAndPregunta(AcogidaModel acogida, PreguntaModel pregunta);
}
