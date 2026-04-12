package adopciones.v01.models.adopciones;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "FotoSeguimiento")
public class FotoSeguimientoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seguimiento_id", nullable = false)
    private SeguimientoModel seguimiento;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "created_at")
    private LocalDateTime created_at;



    //constructor
    public FotoSeguimientoModel(Long id, SeguimientoModel seguimiento, String url, LocalDateTime created_at) {
        this.id = id;
        this.seguimiento = seguimiento;
        this.url = url;
        this.created_at = created_at;
    }
    public FotoSeguimientoModel() {
    }


    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeguimientoModel getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(SeguimientoModel seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
