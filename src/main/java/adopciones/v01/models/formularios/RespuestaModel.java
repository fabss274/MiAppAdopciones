package adopciones.v01.models.formularios;

import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Respuestas")
public class RespuestaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pregunta_id", nullable = false)
    private PreguntaModel pregunta;

    @ManyToOne
    @JoinColumn(name = "adopcion_id")
    private AdopcionModel adopcion;

    @ManyToOne
    @JoinColumn(name = "acogida_id")
    private AcogidaModel acogida;

    @Column(name = "respuesta", columnDefinition = "TEXT")
    private String respuesta;

    @Column(name = "created_at")
    private LocalDateTime created_at;


    //constructores

    public RespuestaModel(Long id, PreguntaModel pregunta, AdopcionModel adopcion, AcogidaModel acogida, String respuesta, LocalDateTime created_at) {
        this.id = id;
        this.pregunta = pregunta;
        this.adopcion = adopcion;
        this.acogida = acogida;
        this.respuesta = respuesta;
        this.created_at = created_at;
    }

    public RespuestaModel() {
    }


    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PreguntaModel getPregunta() {
        return pregunta;
    }

    public void setPregunta(PreguntaModel pregunta) {
        this.pregunta = pregunta;
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

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
