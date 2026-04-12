package adopciones.v01.repositories.adopciones;

import adopciones.v01.models.adopciones.FotoSeguimientoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoSeguimientoRepository extends JpaRepository<FotoSeguimientoModel, Long> {
}
