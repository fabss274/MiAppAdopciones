package adopciones.v01.repositories.comunicacion;

import adopciones.v01.models.comunicacion.MensajeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeModel, Long> {
}
