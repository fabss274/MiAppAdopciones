package adopciones.v01.repositories.formularios;

import adopciones.v01.models.formularios.FormularioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormularioRepository extends JpaRepository<FormularioModel, Long> {
}
