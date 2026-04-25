package adopciones.v01.models.comunicacion;

import adopciones.v01.enums.tipo_solicitud_chat;
import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.refugios.RefugioModel;
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
@Table(name = "Chat")
public class ChatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private AnimalitoModel animal;

    @ManyToOne
    @JoinColumn(name = "solicitante_id", nullable = false)
    private UsuarioModel solicitante;

    @ManyToOne
    @JoinColumn(name = "refugio_id", nullable = false)
    private RefugioModel refugio;

    @Column(name = "tipo_solicitud_chat", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private tipo_solicitud_chat tipo_solicitud_chat;

    @ManyToOne
    @JoinColumn(name = "adopcion_id")
    private AdopcionModel adopcion;

    @ManyToOne
    @JoinColumn(name = "acogida_id")
    private AcogidaModel acogida;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "chat")
    private List<MensajeModel> mensajes;

}
