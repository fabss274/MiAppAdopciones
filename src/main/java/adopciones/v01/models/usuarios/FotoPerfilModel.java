package adopciones.v01.models.usuarios;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "FotoPerfil")
public class FotoPerfilModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne // Asumiendo que es una foto por usuario, si es lista cambiar a @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "created_at")
    private LocalDateTime created_at;

}
