package adopciones.v01.repositories.comunicacion;


import adopciones.v01.models.comunicacion.NotificacionModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<NotificacionModel, Long> {
    List<NotificacionModel> findByUsuarioOrderByFechaDesc(UsuarioModel usuario);
    List<NotificacionModel> findByUsuarioAndLeido(UsuarioModel usuario, boolean leido);

    int countByUsuarioAndLeidoFalse(UsuarioModel usuario);

    @Modifying
    @Query("UPDATE NotificacionModel n SET n.leido = true WHERE n.usuario = :usuario")
    void marcarTodasLeidasPorUsuario(@Param("usuario") UsuarioModel usuario);
}
