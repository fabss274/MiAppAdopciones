package adopciones.v01.models.adopciones;

import adopciones.v01.enums.estadoAdopcion;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.formularios.RespuestaModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Adopciones")
public class AdopcionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private AnimalitoModel animal;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "estadoAdopcion")
    @Enumerated(value = EnumType.STRING)
    private estadoAdopcion estadoAdopcion;

    @Column(name = "motivo_rechazo", columnDefinition = "TEXT")
    private String motivoRechazo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "adopcion")
    private List<SeguimientoModel> seguimientos;

    @OneToMany(mappedBy = "adopcion")
    private List<RespuestaModel> respuestas;

}
