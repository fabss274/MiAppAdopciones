package adopciones.v01.repositories.usuarios;

import adopciones.v01.models.usuarios.PersonaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaModel, Long> {

    boolean existsByDni(String dni);
    Optional<PersonaModel> findByDni(String dni);

    boolean existsByTelefono(String telefono);
    Optional<PersonaModel> findByTelefono(String telefono);

    List<PersonaModel> findByCodpostal(String codPostal);
    List<PersonaModel> findByLocalidad(String localidad);

    //posible ampliacion a provincias
}
