package adopciones.v01.models.comunicacion;

import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Mensaje")
public class MensajeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatModel chat;

    @ManyToOne
    @JoinColumn(name = "remitente_id", nullable = false)
    private UsuarioModel remitente;

    @Column(name = "contenido", nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "fecha_envio")
    private LocalDateTime fecha_envio;

    @Column(name = "leido")
    private boolean leido;


    //constructor
    public MensajeModel(Long id, ChatModel chat, UsuarioModel remitente, String contenido, LocalDateTime fecha_envio, boolean leido) {
        this.id = id;
        this.chat = chat;
        this.remitente = remitente;
        this.contenido = contenido;
        this.fecha_envio = fecha_envio;
        this.leido = leido;
    }
    public MensajeModel() {
    }


    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatModel getChat() {
        return chat;
    }

    public void setChat(ChatModel chat) {
        this.chat = chat;
    }

    public UsuarioModel getRemitente() {
        return remitente;
    }

    public void setRemitente(UsuarioModel remitente) {
        this.remitente = remitente;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(LocalDateTime fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }
}
