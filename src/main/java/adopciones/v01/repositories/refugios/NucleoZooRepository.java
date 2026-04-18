package adopciones.v01.repositories.refugios;

import adopciones.v01.models.refugios.NucleoZooModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NucleoZooRepository extends JpaRepository<NucleoZooModel, Long> {
    Optional<NucleoZooModel> findByCodigoNucleoZoologico(String codigoNucleoZoologico);
}
