package adopciones.v01.models.refugios;

import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.formularios.FormularioModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Perfilrefugio")
public class RefugioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, unique = true)
    private UsuarioModel usuario;

    @Column(name = "nombre_refugio", nullable = false, length = 100)
    private String nombreRefugio;

    @Column(name = "nucleo_zoologico", nullable = false, unique = true, length = 50)
    private String nucleoZoologico;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "verificado")
    private boolean verificado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "refugio")
    private List<AnimalitoModel> animales;

    @OneToMany(mappedBy = "refugio")
    private List<FormularioModel> formularios;


    //constructores

    public RefugioModel(Long id, UsuarioModel usuario, String nombre_refugio, String nucleo_zoologico, String descripcion, String telefono, boolean verificado, LocalDateTime created_at, LocalDateTime updated_at, List<AnimalitoModel> animales, List<FormularioModel> formularios) {
        this.id = id;
        this.usuario = usuario;
        this.nombreRefugio = nombre_refugio;
        this.nucleoZoologico = nucleo_zoologico;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.verificado = verificado;
        this.createdAt = created_at;
        this.updatedAt = updated_at;
        this.animales = animales;
        this.formularios = formularios;
    }

}
