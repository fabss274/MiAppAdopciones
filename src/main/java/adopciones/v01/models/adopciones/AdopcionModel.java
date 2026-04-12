package adopciones.v01.models.adopciones;

import adopciones.v01.enums.estadoAdopcion;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.formularios.RespuestaModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Adopciones")
public class AdopcionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private AnimalitoModel animal;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "estadoAdopcion")
    @Enumerated(value = EnumType.STRING)
    private estadoAdopcion estadoAdopcion;

    @Column(name = "motivo_rechazo", columnDefinition = "TEXT")
    private String motivo_rechazo;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "adopcion")
    private List<SeguimientoModel> seguimientos;

    @OneToMany(mappedBy = "adopcion")
    private List<RespuestaModel> respuestas;


    //constructores
    public AdopcionModel(Long id, AnimalitoModel animal, UsuarioModel usuario, estadoAdopcion estadoAdopcion, String motivo_rechazo, LocalDateTime created_at, LocalDateTime updated_at, List<SeguimientoModel> seguimientos, List<RespuestaModel> respuestas) {
        this.id = id;
        this.animal = animal;
        this.usuario = usuario;
        this.estadoAdopcion = estadoAdopcion;
        this.motivo_rechazo = motivo_rechazo;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.seguimientos = seguimientos;
        this.respuestas = respuestas;
    }
    public AdopcionModel() {}


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

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public estadoAdopcion getEstadoAdopcion() {
        return estadoAdopcion;
    }

    public void setEstadoAdopcion(estadoAdopcion estadoAdopcion) {
        this.estadoAdopcion = estadoAdopcion;
    }

    public String getMotivo_rechazo() {
        return motivo_rechazo;
    }

    public void setMotivo_rechazo(String motivo_rechazo) {
        this.motivo_rechazo = motivo_rechazo;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public List<SeguimientoModel> getSeguimientos() {
        return seguimientos;
    }

    public void setSeguimientos(List<SeguimientoModel> seguimientos) {
        this.seguimientos = seguimientos;
    }

    public List<RespuestaModel> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaModel> respuestas) {
        this.respuestas = respuestas;
    }
}
