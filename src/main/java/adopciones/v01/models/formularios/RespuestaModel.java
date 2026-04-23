package adopciones.v01.models.formularios;

import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Respuestas")
public class RespuestaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pregunta_id", nullable = false)
    private PreguntaModel pregunta;

    @ManyToOne
    @JoinColumn(name = "adopcion_id")
    private AdopcionModel adopcion;

    @ManyToOne
    @JoinColumn(name = "acogida_id")
    private AcogidaModel acogida;

    @Column(name = "respuesta", columnDefinition = "TEXT")
    private String respuesta;

    @Column(name = "created_at")
    private LocalDateTime created_at;

}
