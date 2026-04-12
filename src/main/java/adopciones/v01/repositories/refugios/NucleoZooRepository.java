package adopciones.v01.repositories.refugios;

import adopciones.v01.models.refugios.NucleoZooModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NucleoZooRepository extends JpaRepository<NucleoZooModel, Long> {
}
