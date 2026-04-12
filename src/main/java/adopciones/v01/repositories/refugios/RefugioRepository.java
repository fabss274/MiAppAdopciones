package adopciones.v01.repositories.refugios;

import adopciones.v01.models.refugios.RefugioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefugioRepository extends JpaRepository<RefugioModel, Long> {
}
