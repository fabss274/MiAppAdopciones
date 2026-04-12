package adopciones.v01.models.formularios;

import adopciones.v01.enums.tipoFormulario;
import adopciones.v01.models.refugios.RefugioModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Formulario")
public class FormularioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipoFormulario", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private tipoFormulario tipoFormulario; // Enum: ADOPCION, ACOGIDA

    @ManyToOne
    @JoinColumn(name = "refugio_id", nullable = false)
    private RefugioModel refugio;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "formulario")
    private List<PreguntaModel> preguntas;



    //constructores
    public FormularioModel(Long id, tipoFormulario tipoFormulario, RefugioModel refugio, boolean activo, LocalDateTime created_at, LocalDateTime updated_at, List<PreguntaModel> preguntas) {
        this.id = id;
        this.tipoFormulario = tipoFormulario;
        this.refugio = refugio;
        this.activo = activo;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.preguntas = preguntas;
    }
    public FormularioModel() {
    }


    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public tipoFormulario getTipoFormulario() {
        return tipoFormulario;
    }

    public void setTipoFormulario(tipoFormulario tipoFormulario) {
        this.tipoFormulario = tipoFormulario;
    }

    public RefugioModel getRefugio() {
        return refugio;
    }

    public void setRefugio(RefugioModel refugio) {
        this.refugio = refugio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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

    public List<PreguntaModel> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaModel> preguntas) {
        this.preguntas = preguntas;
    }
}
