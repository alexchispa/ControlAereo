package es.cic.controlaereo.Back.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import es.cic.controlaereo.Back.model.Nave;

public interface NaveRepositorio extends JpaRepository<Nave, Long>{
    Nave findByNombre(String nombre);

}
