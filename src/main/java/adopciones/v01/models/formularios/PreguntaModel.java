package adopciones.v01.models.formularios;

import adopciones.v01.enums.tipoSintaxisPregunta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Preguntas")
public class PreguntaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "form_id", nullable = false)
    private FormularioModel formulario;

    @Column(name = "pregunta", nullable = false)
    private String pregunta;

    @Column(name = "tipoSintaxisPregunta")
    @Enumerated(value = EnumType.STRING)
    private tipoSintaxisPregunta tipoSintaxisPregunta; // Enum: TEXT, BOOLEAN, NUMBER

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "obligatoria")
    private boolean obligatoria;

    @OneToMany(mappedBy = "pregunta")
    private List<RespuestaModel> respuestas;
}
