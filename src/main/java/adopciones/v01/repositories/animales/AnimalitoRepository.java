package adopciones.v01.repositories.animales;

import adopciones.v01.enums.estadoAnimal;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.refugios.RefugioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalitoRepository extends JpaRepository<AnimalitoModel, Long> {

    Optional<AnimalitoModel> findByChip(String chip);
    Optional<AnimalitoModel> findByNumFicha(String numFicha);

    boolean existsByChip(String chip);
    boolean existsByNumFicha(String numFicha);

    List<AnimalitoModel> findByRefugio(RefugioModel refugio);
    List<AnimalitoModel> findByEspecie(String especie);
    List<AnimalitoModel> findByRaza(String raza);
    List<AnimalitoModel> findByEstadoAnimal(estadoAnimal estado);
    List<AnimalitoModel> findByEspecieAndEstadoAnimal(String especie, estadoAnimal estado);
}
