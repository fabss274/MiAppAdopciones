package adopciones.v01.repositories.comunicacion;

import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.comunicacion.ChatModel;
import adopciones.v01.models.refugios.RefugioModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatRepository extends JpaRepository<ChatModel, Long> {

    List<ChatModel> findBySolicitante(UsuarioModel solicitante);
    List<ChatModel> findByRefugio(RefugioModel refugio);
    List<ChatModel> findByAnimal(AnimalitoModel animal);
    //List<ChatModel> findByTipoSolicitudChat(tipoSolicitudChat tipo);

//    Optional<ChatModel> findBySolicitanteAndAnimalAndTipoSolicitudChat(
//            UsuarioModel solicitante, AnimalitoModel animal, tipoSolicitudChat tipo
//    );

    // Evitar duplicar chat para la misma adopcion/acogida
    boolean existsByAdopcion(AdopcionModel adopcion);
    boolean existsByAcogida(AcogidaModel acogida);
}
