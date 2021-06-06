package caio.caminha.ControleVeiculo.repositories;

import caio.caminha.ControleVeiculo.models.Veiculo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VeiculoRepositoryTest {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Test
    @DisplayName(value = "DeveRetornarUmPage<Usuario>")
    public void findUsuarioEmail(){
        String email = "caminhacaio10@gmail.com";
        Page<Veiculo> usuarios = this.veiculoRepository.findByUsuarioEmail(email, PageRequest.of(1, 10, Sort.Direction.DESC, "id"));
        Assert.assertNotNull(usuarios.getContent());
    }

}
