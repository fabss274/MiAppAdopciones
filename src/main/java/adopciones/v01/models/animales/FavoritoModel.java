package adopciones.v01.models.animales;

import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Favoritos")
public class FavoritoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private AnimalitoModel animal;

    @Column(name = "created_at")
    private LocalDateTime created_at;



    // constructores
    public FavoritoModel(Long id, UsuarioModel usuario, AnimalitoModel animal, LocalDateTime created_at) {
        this.id = id;
        this.usuario = usuario;
        this.animal = animal;
        this.created_at = created_at;
    }
    public FavoritoModel() {

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

    public AnimalitoModel getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalitoModel animal) {
        this.animal = animal;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
