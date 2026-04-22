package adopciones.v01.repositories.refugios;

import adopciones.v01.models.refugios.NucleoZooModel;
import adopciones.v01.models.refugios.RefugioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefugioRepository extends JpaRepository<RefugioModel, Long> {

    boolean existsByNucleoZoologico(String codNZoo);
    Optional<NucleoZooModel> findByNucleoZoologico(String codigoNucleoZoologico);

    boolean Verificado(String codigoNucleZoo);
}
