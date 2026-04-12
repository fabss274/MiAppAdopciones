package adopciones.v01.models.refugios;

import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.formularios.FormularioModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PerfilRefugio")
public class RefugioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, unique = true)
    private UsuarioModel usuario;

    @Column(name = "nombre_refugio", nullable = false, length = 100)
    private String nombre_refugio;

    @Column(name = "nucleo_zoologico", nullable = false, unique = true, length = 50)
    private String nucleo_zoologico;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "verificado")
    private boolean verificado;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "refugio")
    private List<AnimalitoModel> animales;

    @OneToMany(mappedBy = "refugio")
    private List<FormularioModel> formularios;


    //constructores

    public RefugioModel(Long id, UsuarioModel usuario, String nombre_refugio, String nucleo_zoologico, String descripcion, String telefono, boolean verificado, LocalDateTime created_at, LocalDateTime updated_at, List<AnimalitoModel> animales, List<FormularioModel> formularios) {
        this.id = id;
        this.usuario = usuario;
        this.nombre_refugio = nombre_refugio;
        this.nucleo_zoologico = nucleo_zoologico;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.verificado = verificado;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.animales = animales;
        this.formularios = formularios;
    }

    public RefugioModel() {
    }


    // getters y setters

    public List<FormularioModel> getFormularios() {
        return formularios;
    }

    public void setFormularios(List<FormularioModel> formularios) {
        this.formularios = formularios;
    }

    public List<AnimalitoModel> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalitoModel> animales) {
        this.animales = animales;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNucleo_zoologico() {
        return nucleo_zoologico;
    }

    public void setNucleo_zoologico(String nucleo_zoologico) {
        this.nucleo_zoologico = nucleo_zoologico;
    }

    public String getNombre_refugio() {
        return nombre_refugio;
    }

    public void setNombre_refugio(String nombre_refugio) {
        this.nombre_refugio = nombre_refugio;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
