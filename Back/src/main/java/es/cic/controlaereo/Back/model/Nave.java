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
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Nave")
public class Nave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;

    @Size(max = 200)
    @Column(name = "PILOTO")
    private String piloto;

    @Size(max = 100)
    @Column(name = "FECHA_SALIDA")
    private String fecha_salida;

    @Size(max = 100)
    @Column(name = "FECHA_llegada")
    private String fecha_llegada;

    @Size(max = 200)
    @Column(name = "localizacion_salida")
    private String localizacion_salida;

    @Size(max = 200)
    @Column(name = "localizacion_llegada")
    private String localizacion_llegada;

    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @ManyToOne
    @JsonIgnoreProperties("naves")
    @JoinColumn(name = "TIPO_ID")
    private Tipo tipo;

    public Nave() {
    }

    public Nave(Long id, String nombre, String piloto, String fecha_salida, String fecha_llegada, String localizacion_salida, String localizacion_llegada, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.piloto = piloto;
        this.fecha_salida = fecha_salida;
        this.fecha_llegada = fecha_llegada;
        this.localizacion_salida = localizacion_salida;
        this.localizacion_llegada = localizacion_llegada;
        this.descripcion = descripcion;
    }

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

    public String getpiloto() {
        return piloto;
    }

    public void setpiloto(String piloto) {
        this.piloto = piloto;
    }

    public String getfecha_salida() {
        return fecha_salida;
    }

    public void setfecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getlocalizacion_salida() {
        return localizacion_salida;
    }

    public void setlocalizacion_salida(String localizacion_salida) {
        this.localizacion_salida = localizacion_salida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(String fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public String getLocalizacion_llegada() {
        return localizacion_llegada;
    }

    public void setLocalizacion_llegada(String localizacion_llegada) {
        this.localizacion_llegada = localizacion_llegada;
    }

}