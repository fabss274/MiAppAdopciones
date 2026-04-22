package adopciones.v01.repositories.animales;

import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.animales.FavoritoModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<FavoritoModel, Long> {

    List<FavoritoModel> findByUsuario(UsuarioModel usuario);
    List<FavoritoModel> findByAnimal(AnimalitoModel animal);

    Optional<FavoritoModel> findByUsuarioAndAnimal(UsuarioModel usuario, AnimalitoModel animal);
    boolean existsByUsuarioAndAnimal(UsuarioModel usuario, AnimalitoModel animal);

    void deleteByUsuarioAndAnimal(UsuarioModel usuario, AnimalitoModel animal);
}
