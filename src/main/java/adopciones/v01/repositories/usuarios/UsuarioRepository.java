package adopciones.v01.repositories.usuarios;

import adopciones.v01.enums.rol;
import adopciones.v01.models.usuarios.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    boolean existsByEmail(String email);
    Optional<UsuarioModel> findByEmail(String email);

    List<UsuarioModel> findByRolAndActivoTrue(rol rol);
    List<UsuarioModel> findByActivoTrue();
    List<UsuarioModel> findByActivoFalse();

}
