package adopciones.v01.models.comunicacion;

import adopciones.v01.enums.tipoNotificacion;
import adopciones.v01.models.usuarios.UsuarioModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Notificacion")
public class NotificacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "tipoNotificacion")
    @Enumerated(value = EnumType.STRING)
    private tipoNotificacion tipoNotificacion;

    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "leido")
    private boolean leido;

    @Column(name = "fecha")
    private LocalDateTime fecha;

}
