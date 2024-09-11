package es.cic.controlaereo.Back.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.controlaereo.Back.model.Nave;
import es.cic.controlaereo.Back.servicio.NaveServicio;

@RestController
@RequestMapping("/api/naves")
public class NaveControlador {

    @Autowired
    private NaveServicio naveServicio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Nave> addNave(@RequestBody Nave nave) {
            Nave nuevaNave = naveServicio.addNave(nave);
            return ResponseEntity.ok(nuevaNave);
    }

    @GetMapping
    public ResponseEntity<List<Nave>> getAllNaves() {
        List<Nave> naves = naveServicio.getAllNaves();
        return ResponseEntity.ok(naves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nave> getNaveById(@PathVariable Long id) {
        Nave nave = naveServicio.getNaveById(id);
        if (nave != null) {
            return ResponseEntity.ok(nave);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Nave> getNaveByNombre(@PathVariable String nombre) {
        Nave nave = naveServicio.getNaveByNombre(nombre);
        if (nave != null) {
            return ResponseEntity.ok(nave);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Nave> updateNave(@PathVariable Long id, @RequestBody Nave nave) {
        if (naveServicio.existsById(id)) {
            nave.setId(id);
            Nave naveActualizada = naveServicio.updateNave(nave);
            return ResponseEntity.ok(naveActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNaveById(@PathVariable Long id) {
        if (naveServicio.existsById(id)) {
            naveServicio.deleteNaveById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}