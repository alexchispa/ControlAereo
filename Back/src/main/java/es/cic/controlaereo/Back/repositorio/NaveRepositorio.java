package es.cic.controlaereo.Back.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.cic.controlaereo.Back.model.Nave;

@Repository
public interface NaveRepositorio extends JpaRepository<Nave, Long>{
    Nave findByNombre(String nombre);

}
