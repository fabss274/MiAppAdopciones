package adopciones.v01.repositories.usuarios;

import adopciones.v01.models.usuarios.FotoPerfilModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoPerfilRepository extends JpaRepository<FotoPerfilModel, Long> {
}
