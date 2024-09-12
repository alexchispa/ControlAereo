package es.cic.controlaereo.Back;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.controlaereo.Back.controlador.NaveControlador;
import es.cic.controlaereo.Back.controlador.TipoControlador;
import es.cic.controlaereo.Back.model.Nave;
import es.cic.controlaereo.Back.model.Tipo;
import es.cic.controlaereo.Back.servicio.NaveServicio;
import es.cic.controlaereo.Back.servicio.TipoServicio;

@SpringBootTest
@AutoConfigureMockMvc
public class ControladoresUnitarioTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private NaveServicio naveServicio;

    @Mock
    private TipoServicio tipoServicio;

    @InjectMocks
    private NaveControlador naveControlador;

    @InjectMocks
    private TipoControlador tipoControlador;

    @Autowired
    private ObjectMapper objectMapper;

    private Nave nave1;
    private Nave nave2;
    private Tipo tipo1;
    private Tipo tipo2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        tipo1 = new Tipo(1L, "Tipo de prueba 1", "Modelo de prueba 1", "Fabricante de prueba 1", 100, true, true, false);
        tipo2 = new Tipo(2L, "Tipo de prueba 2", "Modelo de prueba 2", "Fabricante de prueba 2", 200, false, false, true);

        nave1 = new Nave(1L, "Nave de prueba 1", "Piloto de prueba 1", 1000.0, 30.0, 50.0, 200, 10, 2023, tipo1);
        nave2 = new Nave(2L, "Nave de prueba 2", "Piloto de prueba 2", 1500.0, 35.0, 60.0, 250, 15, 2023, tipo2);

        // Mocking the existence of Tipo entities
        when(tipoServicio.getTipoById(1L)).thenReturn(tipo1);
        when(tipoServicio.getTipoById(2L)).thenReturn(tipo2);
        
        // Mocking Nave responses
        when(naveServicio.getNaveById(1L)).thenReturn(nave1);
        when(naveServicio.getNaveById(2L)).thenReturn(nave2);
        
        when(naveServicio.getAllNaves()).thenReturn(Arrays.asList(nave1, nave2));
        when(naveServicio.existsById(1L)).thenReturn(true);
        when(naveServicio.existsById(2L)).thenReturn(true);
        
        // Mocking Tipo responses
        when(tipoServicio.getAllTipos()).thenReturn(Arrays.asList(tipo1, tipo2));
        when(tipoServicio.existsById(1L)).thenReturn(true);
        when(tipoServicio.existsById(2L)).thenReturn(true);
    }

    // Tests for NaveControlador

    @Test
    public void testGetAllNaves() throws Exception {
        mockMvc.perform(get("/api/naves"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is(nave1.getNombre())))
                .andExpect(jsonPath("$[1].nombre", is(nave2.getNombre())));
    }

    @Test
    public void testAddNave() throws Exception {
        when(naveServicio.addNave(nave1)).thenReturn(nave1);

        mockMvc.perform(post("/api/naves")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nave1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(nave1.getNombre())));
    }

    @Test
    public void testGetNaveById() throws Exception {
        mockMvc.perform(get("/api/naves/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(nave1.getNombre())));
    }

    @Test
    public void testUpdateNave() throws Exception {
        when(naveServicio.updateNave(nave1)).thenReturn(nave1);

        mockMvc.perform(put("/api/naves/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nave1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(nave1.getNombre())));
    }

    @Test
    public void testDeleteNaveById() throws Exception {
        mockMvc.perform(delete("/api/naves/1"))
                .andExpect(status().isNoContent());

        verify(naveServicio, times(1)).deleteNaveById(1L);
    }

    // Tests for TipoControlador

    @Test
    public void testGetAllTipos() throws Exception {
        mockMvc.perform(get("/api/tipos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is(tipo1.getNombre())))
                .andExpect(jsonPath("$[1].nombre", is(tipo2.getNombre())));
    }

    @Test
    public void testAddTipo() throws Exception {
        when(tipoServicio.addTipo(tipo1)).thenReturn(tipo1);

        mockMvc.perform(post("/api/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipo1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(tipo1.getNombre())));
    }

    @Test
    public void testGetTipoById() throws Exception {
        mockMvc.perform(get("/api/tipos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(tipo1.getNombre())));
    }

    @Test
    public void testUpdateTipo() throws Exception {
        when(tipoServicio.updateTipo(tipo1)).thenReturn(tipo1);

        mockMvc.perform(put("/api/tipos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipo1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(tipo1.getNombre())));
    }

    @Test
    public void testDeleteTipoById() throws Exception {
        mockMvc.perform(delete("/api/tipos/1"))
                .andExpect(status().isNoContent());

        verify(tipoServicio, times(1)).deleteTipoById(1L);
    }
}
