package adopciones.v01.models.refugios;

import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.formularios.FormularioModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
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

}
