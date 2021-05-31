package caio.caminha.ControleVeiculo.inputs;

import caio.caminha.ControleVeiculo.models.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

public class InputUsuario {
    @NotNull @NotEmpty
    @JsonProperty("nome")
    private String nome;
    @NotNull @NotEmpty
    @JsonProperty("email")
    private String email;
    @NotNull @NotEmpty
    @JsonProperty("cpf")
    private String cpf;
    @NotNull @NotEmpty
    @JsonProperty("nascimento")
    private String nascimento;

    public String getCpf() {
        return cpf;
    }

    public Usuario convert() throws ParseException {
        return new Usuario(this.nome, this.email, new BCryptPasswordEncoder().encode(this.cpf), this.nascimento);
    }
}
