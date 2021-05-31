package caio.caminha.ControleVeiculo.outputs;

import caio.caminha.ControleVeiculo.models.Usuario;

import java.time.LocalDate;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
}
