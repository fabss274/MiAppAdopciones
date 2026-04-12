package adopciones.v01.models.usuarios;

import adopciones.v01.enums.rol;
import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.animales.FavoritoModel;
import adopciones.v01.models.refugios.RefugioModel;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Usuario")
@Data
public class UsuarioModel implements UserDetails{

    //atributos
    //id auto incrementado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "rol", nullable = false)
    // tipos de rol: ADOPTANTE, REFUGIO, ACOGIDA, ADMIN
    @Enumerated(EnumType.STRING)
    private rol rol;

    @Column(name = "activo")
    private boolean activo = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    // Relaciones con otras tablas
    //un perfil de adoptante o c.acogida tiene un solo usuario
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private PersonaModel PerfilPersona;
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private RefugioModel PerfilRefugio;

    //un usuario tiene muchos animales en favoritos
    @OneToMany(mappedBy = "usuario")
    private List<FavoritoModel> favoritos;

    //un usuario puede enviar varias solicitudes de adopciones
    @OneToMany(mappedBy = "usuario")
    private List<AdopcionModel> adopcionSolicitadas;

    //un usuario puede enviar varias solicitudes de acogidas
    @OneToMany(mappedBy = "usuario")
    private List<AcogidaModel> acogidaSolicitadas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+rol.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
