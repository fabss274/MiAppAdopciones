package adopciones.v01.models.adopciones;

import jakarta.persistence.*;

import java.time.*;
import java.util.List;

@Entity
@Table(name = "Seguimiento")
public class SeguimientoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "adopcion_id", nullable = false)
    private AdopcionModel adopcion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "seguimiento")
    private List<FotoSeguimientoModel> fotos;


    //constructor
    public SeguimientoModel(Long id, AdopcionModel adopcion, LocalDate fecha, String descripcion, LocalDateTime created_at, List<FotoSeguimientoModel> fotos) {
        this.id = id;
        this.adopcion = adopcion;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.created_at = created_at;
        this.fotos = fotos;
    }

    public SeguimientoModel() {
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdopcionModel getAdopcion() {
        return adopcion;
    }

    public void setAdopcion(AdopcionModel adopcion) {
        this.adopcion = adopcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public List<FotoSeguimientoModel> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoSeguimientoModel> fotos) {
        this.fotos = fotos;
    }
}
