package caio.caminha.ControleVeiculo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "cpf"})})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String password;
    private Date nascimento;


    public Usuario(){}

    public Usuario(String nome, String email, String cpf, String password, String nascimento) throws ParseException {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.nascimento = formato.parse(nascimento);
    }



    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }


    public String getCpf() {
        return cpf;
    }


    public Date getNascimento() {
        return nascimento;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public String toString() {
        return this.cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
