package adopciones.v01.models.animales;

import adopciones.v01.enums.estadoAnimal;
import adopciones.v01.enums.sexo;
import adopciones.v01.enums.tamano;
import adopciones.v01.models.adopciones.AcogidaModel;
import adopciones.v01.models.adopciones.AdopcionModel;
import adopciones.v01.models.refugios.RefugioModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Animalito")
public class AnimalitoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "refugio_id", nullable = false)
    private RefugioModel refugio;

    @Column(name = "chip", unique = true, length = 50)
    private String chip;

    @Column(name = "numFicha", nullable = false, unique = true, length = 100)
    private String numFicha;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "especie", length = 50)
    private String especie;

    @Column(name = "raza", length = 50)
    private String raza;

    @Column(name = "sexo")
    @Enumerated(value = EnumType.STRING)
    private sexo sexo; // Enum: MACHO, HEMBRA

    @Column(name = "tamano")
    @Enumerated(value = EnumType.STRING)
    private tamano tamano; // Enum: PEQUENO, MEDIANO, GRANDE

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "esterilizado")
    private boolean esterilizado;

    @Column(name = "estadoAnimal")
    @Enumerated(value = EnumType.STRING)
    private estadoAnimal estadoAnimal; // Enum: DISPONIBLE, RESERVADO, ADOPTADO, MUERTO

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "animal")
    private List<FotoAnimalModel> fotos;

    @OneToMany(mappedBy = "animal")
    private List<FavoritoModel> favoritos;

    @OneToMany(mappedBy = "animal")
    private List<AdopcionModel> adopciones;

    @OneToMany(mappedBy = "animal")
    private List<AcogidaModel> acogidas;

    //constructores
    public AnimalitoModel(Long id, RefugioModel refugio, String chip, String numFicha, String nombre, String especie, String raza, sexo sexo, tamano tamano, Integer edad, String descripcion, boolean esterilizado, estadoAnimal estadoAnimal, LocalDateTime created_at, LocalDateTime updated_at, List<FotoAnimalModel> fotos, List<FavoritoModel> favoritos, List<AdopcionModel> adopciones, List<AcogidaModel> acogidas) {
        this.id = id;
        this.refugio = refugio;
        this.chip = chip;
        this.numFicha = numFicha;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.tamano = tamano;
        this.edad = edad;
        this.descripcion = descripcion;
        this.esterilizado = esterilizado;
        this.estadoAnimal = estadoAnimal;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.fotos = fotos;
        this.favoritos = favoritos;
        this.adopciones = adopciones;
        this.acogidas = acogidas;
    }
    public AnimalitoModel() {
    }

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefugioModel getRefugio() {
        return refugio;
    }

    public void setRefugio(RefugioModel refugio) {
        this.refugio = refugio;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getNumFicha() {
        return numFicha;
    }

    public void setNumFicha(String numFicha) {
        this.numFicha = numFicha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public sexo getSexo() {
        return sexo;
    }

    public void setSexo(sexo sexo) {
        this.sexo = sexo;
    }

    public tamano getTamano() {
        return tamano;
    }

    public void setTamano(tamano tamano) {
        this.tamano = tamano;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(boolean esterilizado) {
        this.esterilizado = esterilizado;
    }

    public estadoAnimal getEstadoAnimal() {
        return estadoAnimal;
    }

    public void setEstadoAnimal(estadoAnimal estadoAnimal) {
        this.estadoAnimal = estadoAnimal;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public List<FotoAnimalModel> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoAnimalModel> fotos) {
        this.fotos = fotos;
    }

    public List<FavoritoModel> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<FavoritoModel> favoritos) {
        this.favoritos = favoritos;
    }

    public List<AdopcionModel> getAdopciones() {
        return adopciones;
    }

    public void setAdopciones(List<AdopcionModel> adopciones) {
        this.adopciones = adopciones;
    }

    public List<AcogidaModel> getAcogidas() {
        return acogidas;
    }

    public void setAcogidas(List<AcogidaModel> acogidas) {
        this.acogidas = acogidas;
    }
}
