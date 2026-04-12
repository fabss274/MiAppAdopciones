package adopciones.v01.models.comunicacion;

import adopciones.v01.enums.tipo_solicitud_chat;
import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.refugios.RefugioModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Chat")
public class ChatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private AnimalitoModel animal;

    @ManyToOne
    @JoinColumn(name = "solicitante_id", nullable = false)
    private UsuarioModel solicitante;

    @ManyToOne
    @JoinColumn(name = "refugio_id", nullable = false)
    private RefugioModel refugio;

    @Column(name = "tipo_solicitud_chat", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private tipo_solicitud_chat tipo_solicitud_chat;

    @ManyToOne
    @JoinColumn(name = "adopcion_id")
    private AdopcionModel adopcion;

    @ManyToOne
    @JoinColumn(name = "acogida_id")
    private AcogidaModel acogida;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "chat")
    private List<MensajeModel> mensajes;


    //constructores
    public ChatModel(Long id, AnimalitoModel animal, UsuarioModel solicitante, RefugioModel refugio, tipo_solicitud_chat tipo_solicitud_chat, AdopcionModel adopcion, AcogidaModel acogida, LocalDateTime created_at, List<MensajeModel> mensajes) {
        this.id = id;
        this.animal = animal;
        this.solicitante = solicitante;
        this.refugio = refugio;
        this.tipo_solicitud_chat = tipo_solicitud_chat;
        this.adopcion = adopcion;
        this.acogida = acogida;
        this.created_at = created_at;
        this.mensajes = mensajes;
    }
    public ChatModel() {
    }


    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnimalitoModel getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalitoModel animal) {
        this.animal = animal;
    }

    public UsuarioModel getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(UsuarioModel solicitante) {
        this.solicitante = solicitante;
    }

    public RefugioModel getRefugio() {
        return refugio;
    }

    public void setRefugio(RefugioModel refugio) {
        this.refugio = refugio;
    }

    public tipo_solicitud_chat getTipo_solicitud_chat() {
        return tipo_solicitud_chat;
    }

    public void setTipo_solicitud_chat(tipo_solicitud_chat tipo_solicitud_chat) {
        this.tipo_solicitud_chat = tipo_solicitud_chat;
    }

    public AdopcionModel getAdopcion() {
        return adopcion;
    }

    public void setAdopcion(AdopcionModel adopcion) {
        this.adopcion = adopcion;
    }

    public AcogidaModel getAcogida() {
        return acogida;
    }

    public void setAcogida(AcogidaModel acogida) {
        this.acogida = acogida;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public List<MensajeModel> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeModel> mensajes) {
        this.mensajes = mensajes;
    }
}
