package es.cic.controlaereo.Back.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.cic.controlaereo.Back.model.Tipo;

@Repository
public interface TipoRepositorio extends JpaRepository<Tipo, Long>{
    Tipo findByModelo(String nombre);
}