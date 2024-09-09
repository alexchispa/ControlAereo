package es.cic.controlaereo.Back.model;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "MODELO")
    private String modelo;

    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    
    @Column(name = "PESO")
    private double peso;

    @Column(name = "LONGITUD")
    private double longitud;

    @OneToMany(mappedBy = "tipo", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Nave> naves;

    public Tipo(Long id, String modelo, String descripcion, double peso, double longitud, ArrayList<Nave> naves) {
        this.id = id;
        this.modelo = modelo;
        this.descripcion = descripcion;
        this.peso = peso;
        this.longitud = longitud;
        this.naves = naves;
    }

    public Tipo(Long id, String modelo, String descripcion, double peso, double longitud) {
        this.id = id;
        this.modelo = modelo;
        this.descripcion = descripcion;
        this.peso = peso;
        this.longitud = longitud;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Tipo() {
    this.naves = new ArrayList<>();
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public List<Nave> getNaves() {
        return naves;
    }
    
    public void setNaves(ArrayList<Nave> naves) {
        this.naves = naves;
    }

    public void addNave(Nave nave) {
        this.naves.add(nave);
        nave.setTipo(this);
    }

}
