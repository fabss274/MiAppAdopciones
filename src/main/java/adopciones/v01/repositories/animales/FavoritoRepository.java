package adopciones.v01.repositories.animales;

import adopciones.v01.models.animales.FavoritoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<FavoritoModel, Long> {
}
