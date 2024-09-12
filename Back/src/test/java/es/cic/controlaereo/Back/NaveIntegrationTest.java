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

import es.cic.controlaereo.Back.model.Nave;
import es.cic.controlaereo.Back.model.Tipo;
import es.cic.controlaereo.Back.repositorio.NaveRepositorio;
import es.cic.controlaereo.Back.repositorio.TipoRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
public class NaveIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NaveRepositorio naveRepositorio;

    @Autowired
    private TipoRepositorio tipoRepositorio;

    @Autowired
    private ObjectMapper objectMapper;

    private Nave nave1;
    private Nave nave2;
    private Tipo tipo;

    @BeforeEach
    public void setup() {
        naveRepositorio.deleteAll();
        tipoRepositorio.deleteAll();

        tipo = new Tipo();
        tipo.setNombre("Tipo de prueba");
		tipo.setModelo("Modelo de prueba");
        tipo.setFabricante("Fabricante de prueba");
        tipo.setCapacidad(100);
        tipo.setEsCivil(true);
        tipo.setEsAerea(true);
        tipo.setEsDeCarga(false);
		
		nave1 = new Nave(null, "Nave de prueba 1", "Piloto de prueba 1", 1000.0, 30.0, 50.0, 200, 10, 2023, tipo);
		nave1.setBase("Base de prueba 1");
		nave1.setColores("Colores de prueba 1");
	
		nave2 = new Nave(null, "Nave de prueba 2", "Piloto de prueba 2", 1500.0, 35.0, 60.0, 250, 15, 2023, tipo);
		nave2.setBase("Base de prueba 2");
		nave2.setColores("Colores de prueba 2");
	
        tipoRepositorio.save(tipo);
        naveRepositorio.save(nave1);
        naveRepositorio.save(nave2);
    }

    @Test
    public void testAddNave() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/naves")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nave1)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Nave createdNave = objectMapper.readValue(content, Nave.class);

        assertThat(createdNave).isNotNull();
        assertThat(createdNave.getId()).isNotNull();
        assertThat(createdNave.getNombre()).isEqualTo(nave1.getNombre());
    }

	
    @Test
    public void testGetAllNaves() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/naves"))
                .andExpect(status().isOk())
                .andReturn();

        List<Nave> naves = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Nave>>() {});
        assertThat(naves).hasSize(2);
    }

    @Test
    public void testGetNaveById() throws Exception {
        Nave savedNave = naveRepositorio.save(nave1);

        MvcResult result = mockMvc.perform(get("/api/naves/" + savedNave.getId()))
                .andExpect(status().isOk())
                .andReturn();

        Nave fetchedNave = objectMapper.readValue(result.getResponse().getContentAsString(), Nave.class);
        assertThat(fetchedNave.getId()).isEqualTo(savedNave.getId());
    }

    @Test
    public void testUpdateNave() throws Exception {
        Nave savedNave = naveRepositorio.save(nave1);
        savedNave.setNombre("Nave actualizada");

        MvcResult result = mockMvc.perform(put("/api/naves/" + savedNave.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedNave)))
                .andExpect(status().isOk())
                .andReturn();

        Nave updatedNave = objectMapper.readValue(result.getResponse().getContentAsString(), Nave.class);
        assertThat(updatedNave.getNombre()).isEqualTo("Nave actualizada");
    }

    @Test
    public void testDeleteNaveById() throws Exception {
        Nave savedNave = naveRepositorio.save(nave1);

        mockMvc.perform(delete("/api/naves/" + savedNave.getId()))
                .andExpect(status().isNoContent());

        assertThat(naveRepositorio.existsById(savedNave.getId())).isFalse();
    }
}