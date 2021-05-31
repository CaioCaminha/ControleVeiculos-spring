package caio.caminha.ControleVeiculo.inputs;

import caio.caminha.ControleVeiculo.models.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

public class InputUsuario {
    @NotNull @NotEmpty
    private String nome;
    @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty
    private String cpf;
    @NotNull @NotEmpty
    private String nascimento;

    public String getCpf() {
        return cpf;
    }

    public Usuario convert() throws ParseException {
        return new Usuario(this.nome, this.email, new BCryptPasswordEncoder().encode(this.cpf), this.nascimento);
    }
}
