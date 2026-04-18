package adopciones.v01.models.refugios;

import adopciones.v01.enums.estadoNucleo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "NucleoZoologico")
@Data
public class NucleoZooModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_nucleo_zoologico", nullable = false, unique = true, length = 50)
    private String codigoNucleoZoologico;

    @Column(name = "nombre_titular_nucleo_zoologico")
    private String nombreTitularNucleoZoologico;

    @Column(name = "especie_grupo_de_especies", columnDefinition = "TEXT")
    private String especieGrupoDeEspecies;

    @Column(name = "muni_exp", length = 100)
    private String muniExp;

    @Column(name = "prov_exp", length = 100)
    private String provExp;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "estadoNucleo")
    @Enumerated(value = EnumType.STRING)
    private estadoNucleo estadoNucleo; // Enum: ACTIVO, INACTIVO

    @Column(name = "ultima_sincronizacion")
    private LocalDateTime ultimaSincronizacion;



    //constructor
    public NucleoZooModel(Long id, String codigoNucleoZoologico, String nombreTitularNucleoZoologico, String especieGrupoDeEspecies, String muniExp, String provExp, LocalDate fecha, estadoNucleo estadoNucleo, LocalDateTime ultimaSincronizacion) {
        this.id = id;
        this.codigoNucleoZoologico = codigoNucleoZoologico;
        this.nombreTitularNucleoZoologico = nombreTitularNucleoZoologico;
        this.especieGrupoDeEspecies = especieGrupoDeEspecies;
        this.muniExp = muniExp;
        this.provExp = provExp;
        this.fecha = fecha;
        this.estadoNucleo = estadoNucleo;
        this.ultimaSincronizacion = ultimaSincronizacion;
    }
    public NucleoZooModel() {
    }
}
