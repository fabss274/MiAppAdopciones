package adopciones.v01.repositories.adopciones;

import adopciones.v01.enums.estadoAdopcion;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdopcionRepository extends JpaRepository<AdopcionModel, Long> {
    List<AdopcionModel> findByUsuario(UsuarioModel usuario);
    List<AdopcionModel> findByAnimal(AnimalitoModel animal);
    List<AdopcionModel> findByEstadoAdopcion(estadoAdopcion estado);

    List<AdopcionModel> findByUsuarioAndEstadoAdopcion(UsuarioModel usuario, estadoAdopcion estado);
    List<AdopcionModel> findByAnimalAndEstadoAdopcion(AnimalitoModel animal, estadoAdopcion estado);

    // Validar solicitud activa duplicada (no RECHAZADA) para mismo usuario+animal
    boolean existsByUsuarioAndAnimalAndEstadoAdopcionNot(
            UsuarioModel usuario, AnimalitoModel animal, estadoAdopcion estado
    );

    Optional<AdopcionModel> findTopByAnimalAndEstadoAdopcionOrderByCreatedAtDesc(
            AnimalitoModel animal, estadoAdopcion estado
    );
}
