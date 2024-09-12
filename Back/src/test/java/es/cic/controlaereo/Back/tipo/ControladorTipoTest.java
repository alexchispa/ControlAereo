package es.cic.controlaereo.Back.tipo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.controlaereo.Back.controlador.TipoControlador;
import es.cic.controlaereo.Back.model.Tipo;
import es.cic.controlaereo.Back.servicio.TipoServicio;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControladorTipoTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TipoServicio tipoServicio;

    @InjectMocks
    private TipoControlador tipoControlador;

    @Autowired
    private ObjectMapper objectMapper;

    private Tipo tipo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        tipo = new Tipo(1L, "Tipo de prueba", "Modelo de prueba", "Fabricante de prueba", 100, true, true, false);
    }

    @Test
    public void testGetTipoById() throws Exception {
        when(tipoServicio.getTipoById(1L)).thenReturn(tipo);

        mockMvc.perform(get("/api/tipos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tipo de prueba"));
    }

    @Test
    public void testCreateTipo() throws Exception {
        when(tipoServicio.addTipo(any(Tipo.class))).thenAnswer(invocation -> {
            Tipo tipoToSave = invocation.getArgument(0);
            tipoToSave.setId(1L); // Simulate persisting Tipo
            return tipoToSave;
        });

        mockMvc.perform(post("/api/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tipo de prueba"));
    }

    @Test
    public void testUpdateTipo() throws Exception {
        when(tipoServicio.existsById(1L)).thenReturn(true);
        when(tipoServicio.updateTipo(any(Tipo.class))).thenReturn(tipo);

        mockMvc.perform(put("/api/tipos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tipo de prueba"));
    }

    @Test
    public void testDeleteTipo() throws Exception {
        when(tipoServicio.existsById(1L)).thenReturn(true);
        doNothing().when(tipoServicio).deleteTipoById(1L);

        mockMvc.perform(delete("/api/tipos/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}