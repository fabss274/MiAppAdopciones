package adopciones.v01.repositories.comunicacion;

import adopciones.v01.models.comunicacion.ChatModel;
import adopciones.v01.models.comunicacion.MensajeModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeModel, Long> {
    List<MensajeModel> findByChatOrderByFechaEnvioAsc(ChatModel chat);
    //List<MensajeModel> findByRemitente(UsuarioModel remitente);

    // Mensajes no leidos en un chat para un destinatario concreto
    //List<MensajeModel> findByChatAndLeidoFalseAndRemitenteNot(ChatModel chat, UsuarioModel remitente);

    // Contar no leidos en un chat
    int countByChatAndLeidoFalseAndRemitenteNot(ChatModel chat, UsuarioModel remitente);

    // Marcar todos como leídos en un chat excepto los propios
    @Modifying
    @Query("UPDATE MensajeModel m SET m.leido = true WHERE m.chat = :chat AND m.remitente <> :remitente")
    void marcarTodosLeidosEnChat(@Param("chat") ChatModel chat, @Param("remitente") UsuarioModel remitente);
}
