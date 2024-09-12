package es.cic.controlaereo.Back;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.controlaereo.Back.model.Tipo;
import es.cic.controlaereo.Back.repositorio.TipoRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
public class TipoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TipoRepositorio tipoRepositorio;

    @Autowired
    private ObjectMapper objectMapper;

    private Tipo tipo;

    @BeforeEach
    public void setup() {
        tipoRepositorio.deleteAll();
        tipo = new Tipo(1L, "Tipo de prueba", "Modelo de prueba", "Fabricante de prueba", 100, true, true, false);
    }

    @Test
    public void testAddTipo() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipo)))
                .andExpect(status().isOk())
                .andReturn();

        Tipo nuevoTipo = objectMapper.readValue(result.getResponse().getContentAsString(), Tipo.class);
        assertThat(nuevoTipo.getId()).isNotNull();
        assertThat(nuevoTipo.getNombre()).isEqualTo(tipo.getNombre());
    }

    @Test
    public void testGetAllTipos() throws Exception {
        tipoRepositorio.save(tipo);

        MvcResult result = mockMvc.perform(get("/api/tipos"))
                .andExpect(status().isOk())
                .andReturn();

        List<Tipo> tipos = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Tipo>>() {});
        assertThat(tipos).hasSize(1);
    }

    @Test
    public void testGetTipoById() throws Exception {
        Tipo savedTipo = tipoRepositorio.save(tipo);

        MvcResult result = mockMvc.perform(get("/api/tipos/" + savedTipo.getId()))
                .andExpect(status().isOk())
                .andReturn();

        Tipo fetchedTipo = objectMapper.readValue(result.getResponse().getContentAsString(), Tipo.class);
        assertThat(fetchedTipo.getId()).isEqualTo(savedTipo.getId());
    }

    @Test
    public void testGetTipoByModelo() throws Exception {
        tipoRepositorio.save(tipo);

        MvcResult result = mockMvc.perform(get("/api/tipos/modelo/" + tipo.getModelo()))
                .andExpect(status().isOk())
                .andReturn();

        Tipo fetchedTipo = objectMapper.readValue(result.getResponse().getContentAsString(), Tipo.class);
        assertThat(fetchedTipo.getModelo()).isEqualTo(tipo.getModelo());
    }

    @Test
    public void testUpdateTipo() throws Exception {
        Tipo savedTipo = tipoRepositorio.save(tipo);
        savedTipo.setNombre("Nombre actualizado");

        MvcResult result = mockMvc.perform(put("/api/tipos/" + savedTipo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedTipo)))
                .andExpect(status().isOk())
                .andReturn();

        Tipo updatedTipo = objectMapper.readValue(result.getResponse().getContentAsString(), Tipo.class);
        assertThat(updatedTipo.getNombre()).isEqualTo("Nombre actualizado");
    }

    @Test
    public void testDeleteTipoById() throws Exception {
        Tipo savedTipo = tipoRepositorio.save(tipo);

        mockMvc.perform(delete("/api/tipos/" + savedTipo.getId()))
                .andExpect(status().isNoContent());

        assertThat(tipoRepositorio.existsById(savedTipo.getId())).isFalse();
    }
}