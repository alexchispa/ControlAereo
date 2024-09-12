package es.cic.controlaereo.Back.tipo;

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

import es.cic.controlaereo.Back.model.Tipo;
import es.cic.controlaereo.Back.repositorio.TipoRepositorio;
import es.cic.controlaereo.Back.servicio.TipoServicio;

public class TipoServicioTest {

    @Mock
    private TipoRepositorio tipoRepositorio;

    @InjectMocks
    private TipoServicio tipoServicio;

    private Tipo tipo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tipo = new Tipo();
        tipo.setId(1L);
        tipo.setNombre("Tipo de prueba");
        tipo.setModelo("Modelo de prueba");
        tipo.setFabricante("Fabricante de prueba");
        tipo.setCapacidad(100);
        tipo.setEsCivil(true);
        tipo.setEsAerea(true);
        tipo.setEsDeCarga(false);
    }

    @Test
    public void testGetAllTipos() {
        when(tipoRepositorio.findAll()).thenReturn(Arrays.asList(tipo));

        List<Tipo> tipos = tipoServicio.getAllTipos();
        assertThat(tipos).hasSize(1);
        assertThat(tipos.get(0).getNombre()).isEqualTo("Tipo de prueba");
    }

    @Test
    public void testGetTipoById() {
        when(tipoRepositorio.findById(tipo.getId())).thenReturn(Optional.of(tipo));

        Tipo foundTipo = tipoServicio.getTipoById(tipo.getId());
        assertThat(foundTipo).isNotNull();
        assertThat(foundTipo.getNombre()).isEqualTo("Tipo de prueba");
    }

    @Test
    public void testGetTipoByIdNotFound() {
        when(tipoRepositorio.findById(tipo.getId())).thenReturn(Optional.empty());

        Tipo foundTipo = tipoServicio.getTipoById(tipo.getId());
        assertThat(foundTipo).isNull();
    }

    @Test
    public void testCreateTipo() {
        when(tipoRepositorio.save(any(Tipo.class))).thenReturn(tipo);

        Tipo createdTipo = tipoServicio.addTipo(tipo);
        assertThat(createdTipo).isNotNull();
        assertThat(createdTipo.getNombre()).isEqualTo("Tipo de prueba");
    }

    @Test
    public void testUpdateTipo() {
        when(tipoRepositorio.findById(tipo.getId())).thenReturn(Optional.of(tipo));
        when(tipoRepositorio.save(any(Tipo.class))).thenReturn(tipo);

        Tipo tipoDetails = new Tipo();
        tipoDetails.setId(tipo.getId());
        tipoDetails.setNombre("Tipo actualizado");
        tipoDetails.setModelo("Modelo actualizado");
        tipoDetails.setFabricante("Fabricante actualizado");
        tipoDetails.setCapacidad(200);
        tipoDetails.setEsCivil(false);
        tipoDetails.setEsAerea(false);
        tipoDetails.setEsDeCarga(true);

        Tipo updatedTipo = tipoServicio.updateTipo(tipoDetails);
        assertThat(updatedTipo).isNotNull();
        assertThat(updatedTipo.getNombre()).isEqualTo("Tipo actualizado");
    }

    @Test
    public void testUpdateTipoNotFound() {
        when(tipoRepositorio.findById(tipo.getId())).thenReturn(Optional.empty());

        Tipo tipoDetails = new Tipo();
        tipoDetails.setId(tipo.getId());
        tipoDetails.setNombre("Tipo actualizado");
        tipoDetails.setModelo("Modelo actualizado");
        tipoDetails.setFabricante("Fabricante actualizado");
        tipoDetails.setCapacidad(200);
        tipoDetails.setEsCivil(false);
        tipoDetails.setEsAerea(false);
        tipoDetails.setEsDeCarga(true);

        Tipo updatedTipo = tipoServicio.updateTipo(tipoDetails);
        assertThat(updatedTipo).isNull();
    }

    @Test
    public void testDeleteTipo() {
        when(tipoRepositorio.findById(tipo.getId())).thenReturn(Optional.of(tipo));
        doNothing().when(tipoRepositorio).deleteById(tipo.getId());

        tipoServicio.deleteTipoById(tipo.getId());
        verify(tipoRepositorio, times(1)).deleteById(tipo.getId());
    }

    @Test
    public void testDeleteTipoNotFound() {
        when(tipoRepositorio.findById(tipo.getId())).thenReturn(Optional.empty());

        tipoServicio.deleteTipoById(tipo.getId());
        verify(tipoRepositorio, times(0)).deleteById(tipo.getId());
    }
}