package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.inputs.InputVeiculo;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.outputs.OutputVeiculo;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import caio.caminha.ControleVeiculo.repositories.VeiculoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VeiculoServiceTest {
    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName(value = "DeveRetornarOutputVeiculoDoTipoCarro")
    public void createVeiculoCarroTest() throws Exception{
        InputVeiculo input = new InputVeiculo("carro",
                "VW - VolksWagen",
                "AMAROK CD2.0 16V/S CD2.0 16V TDI 4x2 Die",
                "2013", "Diesel");

        Optional<Usuario> usuario = this.usuarioRepository.findByCpf("62890965325");

        OutputVeiculo veiculoCriado = this.veiculoService.saveVeiculo(input, usuario.get());
        Assert.assertNotNull(veiculoCriado.getId());
        Assert.assertEquals(input.getMarca(), veiculoCriado.getMarca());
        this.veiculoRepository.deleteById(veiculoCriado.getId());
    }

    @Test
    @DisplayName(value = "DeveRetornarOutputVeiculoDoTipoMoto")
    public void createVeiculoMotoTest() throws Exception{
        InputVeiculo input = new InputVeiculo("moto",
                "ADLY",
                "ATV 100",
                "2002", "Gasolina");

        Optional<Usuario> usuario = this.usuarioRepository.findByCpf("62890965325");

        OutputVeiculo veiculoCriado = this.veiculoService.saveVeiculo(input, usuario.get());
        Assert.assertNotNull(veiculoCriado.getId());
        Assert.assertEquals(input.getMarca(), veiculoCriado.getMarca());
        this.veiculoRepository.deleteById(veiculoCriado.getId());
    }

    @Test
    @DisplayName(value = "DeveRetornarOutputVeiculoDoTipoCaminhao")
    public void createVeiculoCaminhaoTest() throws Exception{
        InputVeiculo input = new InputVeiculo("caminhao",
                "AGRALE",
                "10000 / 10000 S  2p (diesel) (E5)",
                "2018", "Diesel");

        Optional<Usuario> usuario = this.usuarioRepository.findByCpf("62890965325");

        OutputVeiculo veiculoCriado = this.veiculoService.saveVeiculo(input, usuario.get());
        Assert.assertNotNull(veiculoCriado.getId());
        Assert.assertEquals(input.getMarca(), veiculoCriado.getMarca());
        this.veiculoRepository.deleteById(veiculoCriado.getId());
    }
}
