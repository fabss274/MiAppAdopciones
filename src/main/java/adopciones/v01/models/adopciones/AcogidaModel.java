package adopciones.v01.models.adopciones;

import adopciones.v01.enums.estadoAcogidas;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.formularios.RespuestaModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Acogidas")
public class AcogidaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private AnimalitoModel animal;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "estadoAcogidas")
    @Enumerated(value = EnumType.STRING)
    private estadoAcogidas estadoAcogidas;

    @Column(name = "motivo_rechazo")
    private String motivo_rechazo;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "acogida")
    private List<RespuestaModel> respuestas;


}
