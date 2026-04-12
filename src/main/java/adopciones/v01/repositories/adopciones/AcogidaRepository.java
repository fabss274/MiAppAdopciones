package adopciones.v01.repositories.adopciones;

import adopciones.v01.models.adopciones.AcogidaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcogidaRepository extends JpaRepository<AcogidaModel, Long> {
}
