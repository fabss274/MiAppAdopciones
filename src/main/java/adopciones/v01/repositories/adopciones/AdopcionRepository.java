package adopciones.v01.repositories.adopciones;

import adopciones.v01.models.adopciones.AdopcionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdopcionRepository extends JpaRepository<AdopcionModel, Long> {
}
