package adopciones.v01.repositories.formularios;

import adopciones.v01.models.formularios.PreguntaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaRepository extends JpaRepository<PreguntaModel, Long> {
}
