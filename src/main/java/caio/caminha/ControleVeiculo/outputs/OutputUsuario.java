package caio.caminha.ControleVeiculo.outputs;

import caio.caminha.ControleVeiculo.models.Usuario;

import java.util.Date;

public class OutputUsuario {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private Date nascimento;

    public OutputUsuario() {}

    public OutputUsuario(Usuario usuario){
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.nascimento = usuario.getNascimento();
    }

    public Long getId() {
        return id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
