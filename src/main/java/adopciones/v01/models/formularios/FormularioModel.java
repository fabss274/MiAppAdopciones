package adopciones.v01.models.formularios;

import adopciones.v01.enums.tipoFormulario;
import adopciones.v01.models.refugios.RefugioModel;
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
@Table(name = "Formulario")
public class FormularioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipoFormulario", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private tipoFormulario tipoFormulario; // Enum: ADOPCION, ACOGIDA

    @ManyToOne
    @JoinColumn(name = "refugio_id", nullable = false)
    private RefugioModel refugio;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "formulario")
    private List<PreguntaModel> preguntas;

}
