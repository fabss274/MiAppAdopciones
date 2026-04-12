package adopciones.v01.models.formularios;

import adopciones.v01.enums.tipoSintaxisPregunta;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Preguntas")
public class PreguntaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "form_id", nullable = false)
    private FormularioModel formulario;

    @Column(name = "pregunta", nullable = false, length = 255)
    private String pregunta;

    @Column(name = "tipoSintaxisPregunta")
    @Enumerated(value = EnumType.STRING)
    private tipoSintaxisPregunta tipoSintaxisPregunta; // Enum: TEXT, BOOLEAN, NUMBER

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "obligatoria")
    private boolean obligatoria;

    @OneToMany(mappedBy = "pregunta")
    private List<RespuestaModel> respuestas;


    //constructores
    public PreguntaModel(Long id, FormularioModel formulario, String pregunta, tipoSintaxisPregunta tipoSintaxisPregunta, Integer orden, boolean obligatoria, List<RespuestaModel> respuestas) {
        this.id = id;
        this.formulario = formulario;
        this.pregunta = pregunta;
        this.tipoSintaxisPregunta = tipoSintaxisPregunta;
        this.orden = orden;
        this.obligatoria = obligatoria;
        this.respuestas = respuestas;
    }
    public PreguntaModel() {
    }


    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormularioModel getFormulario() {
        return formulario;
    }

    public void setFormulario(FormularioModel formulario) {
        this.formulario = formulario;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public tipoSintaxisPregunta getTipoSintaxisPregunta() {
        return tipoSintaxisPregunta;
    }

    public void setTipoSintaxisPregunta(tipoSintaxisPregunta tipoSintaxisPregunta) {
        this.tipoSintaxisPregunta = tipoSintaxisPregunta;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public List<RespuestaModel> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaModel> respuestas) {
        this.respuestas = respuestas;
    }
}
