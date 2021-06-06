package caio.caminha.ControleVeiculo.inputs;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public class AuthInput {
    @JsonProperty("cpf")
    private String cpf;
    @JsonProperty("password")
    private String password;

    public AuthInput(){}
    public AuthInput(String cpf, String password){
        this.cpf = cpf;
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }

    public UsernamePasswordAuthenticationToken convert(){
        return new UsernamePasswordAuthenticationToken(cpf, password);
    }

}
