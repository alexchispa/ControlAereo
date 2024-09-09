package es.cic.controlaereo.Back.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.controlaereo.Back.model.Tipo;
import es.cic.controlaereo.Back.repositorio.TipoRepositorio;

@Service
public class TipoServicio {

    @Autowired
    private TipoRepositorio tipoRepositorio;

    public Tipo addTipo(Tipo tipo) {
        return tipoRepositorio.save(tipo);
    }

    public List<Tipo> getAllTipos() {
        return tipoRepositorio.findAll();
    }

    public Tipo getTipoByModelo(String modelo) {
        return tipoRepositorio.findByModelo(modelo);
    }

    public Tipo updateTipo(Tipo tipo) {
        return tipoRepositorio.save(tipo);
    }

    public boolean existsById(Long id) {
        return tipoRepositorio.existsById(id);
    }

    public Tipo getTipoById(Long id) {
        return tipoRepositorio.findById(id).orElse(null);
    }

    public void deleteTipoById(Long id) {
        tipoRepositorio.deleteById(id);
    }
}