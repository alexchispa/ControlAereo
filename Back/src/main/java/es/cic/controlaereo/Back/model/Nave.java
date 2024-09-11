package es.cic.controlaereo.Back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Nave")
public class Nave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(nullable = false, name = "NOMBRE")
    private String nombre;

    @Size(max = 200)
    @Column(nullable = true, name = "PILOTO")
    private String piloto;

    @NotNull
    @Size(max = 200)
    @Column(nullable = false, name = "COLORES")
    private String colores;

    @NotNull
    @Size(max = 200)
    @Column(nullable = false, name = "BASE")
    private String base;

    @Size(max = 500)
    @Column(nullable = true, name = "MODIFICACIONES")
    private String modificaciones;

    @NotNull
    @Min(1900)
    @Max(2100)
    @Column(nullable = false, name = "ANYO_FABRICACION")
    private Integer anyoFabricacion;

    @NotNull
    @Column(nullable = false, name = "PESO", columnDefinition = "DECIMAL(10, 3)")
    private Double peso;

    @NotNull
    @Column(nullable = false, name = "LONGITUD", columnDefinition = "DECIMAL(10, 2)")
    private Double longitud;

    @NotNull
    @Column(nullable = false, name = "BODEGA", columnDefinition = "DECIMAL(10, 2)")
    private Double bodega;

    @NotNull
    @Column(nullable = false, name = "CARGA")
    private Integer carga;

    @NotNull
    @Column(nullable = false, name = "CAPACIDAD_PERSONAS")
    private Integer capacidadPersonas;

    @ManyToOne
    @JsonIgnoreProperties("naves")
    @JoinColumn(name = "TIPO_ID", nullable = true)
    private Tipo tipo;

    public Nave() {
    }

    public Nave(Long id, String nombre, String piloto, Double peso, Double longitud, Double bodega, Integer carga, Integer capacidadPersonas, Integer anyoFabricacion, Tipo tipo) {
        this.id = id;
        this.nombre = nombre;
        this.piloto = piloto;
        this.peso = peso;
        this.longitud = longitud;
        this.bodega = bodega;
        this.carga = carga;
        this.capacidadPersonas = capacidadPersonas;
        this.anyoFabricacion = anyoFabricacion;
        this.tipo = tipo;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPiloto() {
        return piloto;
    }

    public void setPiloto(String piloto) {
        this.piloto = piloto;
    }

    public String getColores() {
        return colores;
    }

    public void setColores(String colores) {
        this.colores = colores;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getModificaciones() {
        return modificaciones;
    }

    public void setModificaciones(String modificaciones) {
        this.modificaciones = modificaciones;
    }

    public Integer getAnyoFabricacion() {
        return anyoFabricacion;
    }

    public void setAnyoFabricacion(Integer anyoFabricacion) {
        this.anyoFabricacion = anyoFabricacion;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getBodega() {
        return bodega;
    }

    public void setBodega(Double bodega) {
        this.bodega = bodega;
    }

    public Integer getCarga() {
        return carga;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
    }

    public Integer getCapacidadPersonas() {
        return capacidadPersonas;
    }

    public void setCapacidadPersonas(Integer capacidadPersonas) {
        this.capacidadPersonas = capacidadPersonas;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}