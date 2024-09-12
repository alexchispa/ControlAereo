package es.cic.controlaereo.Back.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Tipo")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Size(max = 200)
    @Column(nullable = false, name = "NOMBRE")
    private String nombre;

    @Size(max = 200)
    @Column(nullable = false, name = "MODELO")
    private String modelo;

    @Size(max = 200)
    @Column(nullable = false, name = "FABRICANTE")
    private String fabricante;

    @Column(nullable = false, name = "CAPACIDAD")
    private Integer capacidad;

    @Column(nullable = false, name = "ES_CIVIL")
    private boolean esCivil;

    @Column(nullable = false, name = "ES_AEREA")
    private boolean esAerea;

    @Column(nullable = false, name = "ES_DE_CARGA")
    private boolean esDeCarga;

    @OneToMany(mappedBy = "tipo", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Nave> naves;

    // Constructor sin parámetros (obligatorio para Jackson)
    public Tipo() {
        this.naves = new ArrayList<>();
    }

    // Constructor con anotaciones @JsonProperty
    @JsonCreator
    public Tipo(
        @JsonProperty("id") Long id,
        @JsonProperty("nombre") String nombre,
        @JsonProperty("modelo") String modelo,
        @JsonProperty("fabricante") String fabricante,
        @JsonProperty("capacidad") Integer capacidad,
        @JsonProperty("esCivil") boolean esCivil,
        @JsonProperty("esAerea") boolean esAerea,
        @JsonProperty("esDeCarga") boolean esDeCarga,
        @JsonProperty("naves") List<Nave> naves
    ) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.capacidad = capacidad;
        this.esCivil = esCivil;
        this.esAerea = esAerea;
        this.esDeCarga = esDeCarga;
        this.naves = naves != null ? naves : new ArrayList<>();
    }

    public Tipo(Long id, String nombre, String modelo, String fabricante, Integer capacidad, boolean esCivil, boolean esAerea, boolean esDeCarga) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.capacidad = capacidad;
        this.esCivil = esCivil;
        this.esAerea = esAerea;
        this.esDeCarga = esDeCarga;
        this.naves = new ArrayList<>();
    }

    // Getters y setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isEsCivil() {
        return esCivil;
    }

    public void setEsCivil(boolean esCivil) {
        this.esCivil = esCivil;
    }

    public boolean isEsAerea() {
        return esAerea;
    }

    public void setEsAerea(boolean esAerea) {
        this.esAerea = esAerea;
    }

    public boolean isEsDeCarga() {
        return esDeCarga;
    }

    public void setEsDeCarga(boolean esDeCarga) {
        this.esDeCarga = esDeCarga;
    }

    public List<Nave> getNaves() {
        return naves;
    }

    public void setNaves(List<Nave> naves) {
        this.naves = naves;
    }
}