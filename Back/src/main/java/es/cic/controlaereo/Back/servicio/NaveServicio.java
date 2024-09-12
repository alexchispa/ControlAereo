package es.cic.controlaereo.Back.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.controlaereo.Back.model.Nave;
import es.cic.controlaereo.Back.repositorio.NaveRepositorio;

@Service
public class NaveServicio {

    @Autowired
    private NaveRepositorio naveRepositorio;

    public Nave addNave(Nave nave) {
        return naveRepositorio.save(nave);
    }

    public List<Nave> getAllNaves() {
        return naveRepositorio.findAll();
    }

    public Nave getNaveByNombre(String nombre) {
        return naveRepositorio.findByNombre(nombre);
    }

    public Nave updateNave(Nave nave) {
        return naveRepositorio.save(nave);
    }

    public boolean existsById(Long id) {
        return naveRepositorio.existsById(id);
    }

    public Nave getNaveById(Long id) {
        Optional<Nave> nave = naveRepositorio.findById(id);
        return nave.orElse(null);
    }

    public void deleteNaveById(Long id) {
        if (naveRepositorio.existsById(id)) {
            naveRepositorio.deleteById(id);
        }
    }
}