package adopciones.v01.models.refugios;

import adopciones.v01.enums.estadoNucleo;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "NucleoZoologico")
public class NucleoZooModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_nucleo_zoologico", nullable = false, unique = true, length = 50)
    private String codigo_nucleo_zoologico;

    @Column(name = "nombre_titular_nucleo_zoologico", length = 255)
    private String nombre_titular_nucleo_zoologico;

    @Column(name = "especie_grupo_de_especies", columnDefinition = "TEXT")
    private String especie_grupo_de_especies;

    @Column(name = "muni_exp", length = 100)
    private String muni_exp;

    @Column(name = "prov_exp", length = 100)
    private String prov_exp;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "estadoNucleo")
    @Enumerated(value = EnumType.STRING)
    private estadoNucleo estadoNucleo; // Enum: ACTIVO, INACTIVO

    @Column(name = "ultima_sincronizacion")
    private LocalDateTime ultima_sincronizacion;



    //constructor
    public NucleoZooModel(Long id, String codigo_nucleo_zoologico, String nombre_titular_nucleo_zoologico, String especie_grupo_de_especies, String muni_exp, String prov_exp, LocalDate fecha, estadoNucleo estadoNucleo, LocalDateTime ultima_sincronizacion) {
        this.id = id;
        this.codigo_nucleo_zoologico = codigo_nucleo_zoologico;
        this.nombre_titular_nucleo_zoologico = nombre_titular_nucleo_zoologico;
        this.especie_grupo_de_especies = especie_grupo_de_especies;
        this.muni_exp = muni_exp;
        this.prov_exp = prov_exp;
        this.fecha = fecha;
        this.estadoNucleo = estadoNucleo;
        this.ultima_sincronizacion = ultima_sincronizacion;
    }
    public NucleoZooModel() {
    }



    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo_nucleo_zoologico() {
        return codigo_nucleo_zoologico;
    }

    public void setCodigo_nucleo_zoologico(String codigo_nucleo_zoologico) {
        this.codigo_nucleo_zoologico = codigo_nucleo_zoologico;
    }

    public String getNombre_titular_nucleo_zoologico() {
        return nombre_titular_nucleo_zoologico;
    }

    public void setNombre_titular_nucleo_zoologico(String nombre_titular_nucleo_zoologico) {
        this.nombre_titular_nucleo_zoologico = nombre_titular_nucleo_zoologico;
    }

    public String getEspecie_grupo_de_especies() {
        return especie_grupo_de_especies;
    }

    public void setEspecie_grupo_de_especies(String especie_grupo_de_especies) {
        this.especie_grupo_de_especies = especie_grupo_de_especies;
    }

    public String getMuni_exp() {
        return muni_exp;
    }

    public void setMuni_exp(String muni_exp) {
        this.muni_exp = muni_exp;
    }

    public String getProv_exp() {
        return prov_exp;
    }

    public void setProv_exp(String prov_exp) {
        this.prov_exp = prov_exp;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public estadoNucleo getEstadoNucleo() {
        return estadoNucleo;
    }

    public void setEstadoNucleo(estadoNucleo estadoNucleo) {
        this.estadoNucleo = estadoNucleo;
    }

    public LocalDateTime getUltima_sincronizacion() {
        return ultima_sincronizacion;
    }

    public void setUltima_sincronizacion(LocalDateTime ultima_sincronizacion) {
        this.ultima_sincronizacion = ultima_sincronizacion;
    }
}
