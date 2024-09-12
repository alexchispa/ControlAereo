package es.cic.controlaereo.Back.nave;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import es.cic.controlaereo.Back.model.Nave;
import es.cic.controlaereo.Back.repositorio.NaveRepositorio;
import es.cic.controlaereo.Back.servicio.NaveServicio;

public class NaveRepositorioTest {

    @Mock
    private NaveRepositorio naveRepositorio;

    @InjectMocks
    private NaveServicio naveServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByNombre() {
        // Arrange
        String nombre = "Nave Espacial";
        Nave nave = new Nave();
        nave.setNombre(nombre);

        when(naveRepositorio.findByNombre(nombre)).thenReturn(nave);

        // Act
        Nave result = naveServicio.getNaveByNombre(nombre);

        // Assert
        assertNotNull(result);
        assertEquals(nombre, result.getNombre());
        verify(naveRepositorio, times(1)).findByNombre(nombre);
    }

    @Test
    public void testSaveNave() {
        // Arrange
        Nave nave = new Nave();
        nave.setNombre("Nueva Nave");

        when(naveRepositorio.save(nave)).thenReturn(nave);

        // Act
        Nave result = naveServicio.addNave(nave);

        // Assert
        assertNotNull(result);
        assertEquals("Nueva Nave", result.getNombre());
        verify(naveRepositorio, times(1)).save(nave);
    }

    @Test
    public void testFindAll() {
        // Arrange
        Nave nave1 = new Nave();
        nave1.setNombre("Nave 1");
        Nave nave2 = new Nave();
        nave2.setNombre("Nave 2");

        List<Nave> naves = Arrays.asList(nave1, nave2);

        when(naveRepositorio.findAll()).thenReturn(naves);

        // Act
        List<Nave> result = naveServicio.getAllNaves();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(naveRepositorio, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Long id = 1L;
        doNothing().when(naveRepositorio).deleteById(id);

        // Act
        naveServicio.deleteNaveById(id);

        // Assert
        verify(naveRepositorio, times(1)).deleteById(id);
    }
}