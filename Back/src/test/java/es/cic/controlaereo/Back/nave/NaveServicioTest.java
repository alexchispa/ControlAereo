package es.cic.controlaereo.Back.nave;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import es.cic.controlaereo.Back.model.Nave;
import es.cic.controlaereo.Back.model.Tipo;
import es.cic.controlaereo.Back.repositorio.NaveRepositorio;
import es.cic.controlaereo.Back.servicio.NaveServicio;

public class NaveServicioTest {

    @Mock
    private NaveRepositorio naveRepositorio;

    @InjectMocks
    private NaveServicio naveServicio;

    private Nave nave;
    private Tipo tipo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tipo = new Tipo();
        tipo.setId(1L);
        tipo.setNombre("Tipo de prueba");
        nave = new Nave();
        nave.setId(1L);
        nave.setNombre("Nave de prueba");
        nave.setPiloto("Piloto de prueba");
        nave.setPeso(1000.0);
        nave.setLongitud(50.0);
        nave.setBodega(200.0);
        nave.setCarga(500);
        nave.setCapacidadPersonas(10);
        nave.setAnyoFabricacion(2020);
        nave.setTipo(tipo);
    }

    @Test
    public void testGetAllNaves() {
        when(naveRepositorio.findAll()).thenReturn(Arrays.asList(nave));

        List<Nave> naves = naveServicio.getAllNaves();
        assertThat(naves).hasSize(1);
        assertThat(naves.get(0).getNombre()).isEqualTo("Nave de prueba");
    }

    @Test
    public void testGetNaveById() {
        when(naveRepositorio.findById(nave.getId())).thenReturn(Optional.of(nave));

        Nave foundNave = naveServicio.getNaveById(nave.getId());
        assertThat(foundNave).isNotNull();
        assertThat(foundNave.getNombre()).isEqualTo("Nave de prueba");
    }

    @Test
    public void testGetNaveByIdNotFound() {
        when(naveRepositorio.findById(nave.getId())).thenReturn(Optional.empty());

        Nave foundNave = naveServicio.getNaveById(nave.getId());
        assertThat(foundNave).isNull();
    }

    @Test
    public void testCreateNave() {
        when(naveRepositorio.save(any(Nave.class))).thenReturn(nave);

        Nave createdNave = naveServicio.addNave(nave);
        assertThat(createdNave).isNotNull();
        assertThat(createdNave.getNombre()).isEqualTo("Nave de prueba");
    }

    @Test
    public void testUpdateNave() {
        when(naveRepositorio.findById(nave.getId())).thenReturn(Optional.of(nave));
        when(naveRepositorio.save(any(Nave.class))).thenReturn(nave);

        Nave naveDetails = new Nave();
        naveDetails.setId(nave.getId());
        naveDetails.setNombre("Nave actualizada");
        naveDetails.setPiloto("Piloto actualizado");
        naveDetails.setPeso(1200.0);
        naveDetails.setLongitud(55.0);
        naveDetails.setBodega(250.0);
        naveDetails.setCarga(600);
        naveDetails.setCapacidadPersonas(12);
        naveDetails.setAnyoFabricacion(2021);
        naveDetails.setTipo(tipo);

        Nave updatedNave = naveServicio.updateNave(naveDetails);
        assertThat(updatedNave).isNotNull();
        assertThat(updatedNave.getNombre()).isEqualTo("Nave actualizada");
    }

    @Test
    public void testUpdateNaveNotFound() {
        when(naveRepositorio.findById(nave.getId())).thenReturn(Optional.empty());

        Nave naveDetails = new Nave();
        naveDetails.setId(nave.getId());
        naveDetails.setNombre("Nave actualizada");
        naveDetails.setPiloto("Piloto actualizado");
        naveDetails.setPeso(1200.0);
        naveDetails.setLongitud(55.0);
        naveDetails.setBodega(250.0);
        naveDetails.setCarga(600);
        naveDetails.setCapacidadPersonas(12);
        naveDetails.setAnyoFabricacion(2021);
        naveDetails.setTipo(tipo);

        Nave updatedNave = naveServicio.updateNave(naveDetails);
        assertThat(updatedNave).isNull();
    }

    @Test
    public void testDeleteNave() {
        when(naveRepositorio.findById(nave.getId())).thenReturn(Optional.of(nave));
        doNothing().when(naveRepositorio).deleteById(nave.getId());

        naveServicio.deleteNaveById(nave.getId());
        verify(naveRepositorio, times(1)).deleteById(nave.getId());
    }

    @Test
    public void testDeleteNaveNotFound() {
        when(naveRepositorio.findById(nave.getId())).thenReturn(Optional.empty());

        naveServicio.deleteNaveById(nave.getId());
        verify(naveRepositorio, times(0)).deleteById(nave.getId());
    }
}