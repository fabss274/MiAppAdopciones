package adopciones.v01.repositories.comunicacion;

import adopciones.v01.models.comunicacion.NotificacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<NotificacionModel, Long> {
}
