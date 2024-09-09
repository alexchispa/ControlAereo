package es.cic.controlaereo.Back.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.controlaereo.Back.model.Tipo;
import es.cic.controlaereo.Back.servicio.TipoServicio;

@RestController
@RequestMapping("/api/tipos")
public class TipoControlador {

    @Autowired
    private TipoServicio tipoServicio;

    @PostMapping
    public ResponseEntity<Tipo> addTipo(@RequestBody Tipo tipo) {
        Tipo nuevoTipo = tipoServicio.addTipo(tipo);
        return ResponseEntity.ok(nuevoTipo);
    }

    @GetMapping
    public ResponseEntity<List<Tipo>> getAllTipos() {
        List<Tipo> tipos = tipoServicio.getAllTipos();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo> getTipoById(@PathVariable Long id) {
        Tipo tipo = tipoServicio.getTipoById(id);
        if (tipo != null) {
            return ResponseEntity.ok(tipo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<Tipo> getTipoByModelo(@PathVariable String modelo) {
        Tipo tipo = tipoServicio.getTipoByModelo(modelo);
        if (tipo != null) {
            return ResponseEntity.ok(tipo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> updateTipo(@PathVariable Long id, @RequestBody Tipo tipo) {
        if (tipoServicio.existsById(id)) {
            tipo.setId(id);
            Tipo tipoActualizado = tipoServicio.updateTipo(tipo);
            return ResponseEntity.ok(tipoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoById(@PathVariable Long id) {
        if (tipoServicio.existsById(id)) {
            tipoServicio.deleteTipoById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}