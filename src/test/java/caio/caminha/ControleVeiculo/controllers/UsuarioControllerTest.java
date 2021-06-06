package caio.caminha.ControleVeiculo.controllers;

import caio.caminha.ControleVeiculo.inputs.InputUsuario;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName(value = "DeveRetornar400")
    public void createInvalidUsuarioTest() throws Exception{
        URI uri = new URI("/usuarios");

        InputUsuario input = new InputUsuario(null, null, null, null, null);
        String json = new ObjectMapper().writeValueAsString(input);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName(value = "DeveRetornar201")
    public void createUsuarioTest() throws Exception{
        URI uri = new URI("/usuarios");

        InputUsuario input = new InputUsuario("caio", "caio@email.com", "12346578", "caminha123", "29/11/2002");
        String json = new ObjectMapper().writeValueAsString(input);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty());
        Optional<Usuario> usuarioCriado = this.usuarioRepository.findByCpf(input.getCpf());
        this.usuarioRepository.deleteById(usuarioCriado.get().getId());
    }


}
