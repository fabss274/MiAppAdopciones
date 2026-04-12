package adopciones.v01.models.animales;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "FotoAnimal")
public class FotoAnimalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private AnimalitoModel animal;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "es_principal")
    private boolean es_principal;

    @Column(name = "created_at")
    private LocalDateTime created_at;


    //constructor
    public FotoAnimalModel(Long id, AnimalitoModel animal, String url, boolean es_principal, LocalDateTime created_at) {
        this.id = id;
        this.animal = animal;
        this.url = url;
        this.es_principal = es_principal;
        this.created_at = created_at;
    }
    public FotoAnimalModel() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEs_principal() {
        return es_principal;
    }

    public void setEs_principal(boolean es_principal) {
        this.es_principal = es_principal;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
