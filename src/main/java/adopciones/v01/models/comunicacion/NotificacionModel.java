package adopciones.v01.models.comunicacion;

import adopciones.v01.enums.tipoNotificacion;
import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notificacion")
public class NotificacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "tipoNotificacion")
    @Enumerated(value = EnumType.STRING)
    private tipoNotificacion tipoNotificacion;

    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "leido")
    private boolean leido;

    @Column(name = "fecha")
    private LocalDateTime fecha;


    //constructor

    public NotificacionModel(Long id, UsuarioModel usuario, tipoNotificacion tipoNotificacion, String mensaje, boolean leido, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.tipoNotificacion = tipoNotificacion;
        this.mensaje = mensaje;
        this.leido = leido;
        this.fecha = fecha;
    }
    public NotificacionModel() {
    }


    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public tipoNotificacion getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(tipoNotificacion tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
