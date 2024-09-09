package es.cic.controlaereo.Back.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import es.cic.controlaereo.Back.model.Tipo;

public interface TipoRepositorio extends JpaRepository<Tipo, Long>{
    Tipo findByModelo(String nombre);
}