package caio.caminha.ControleVeiculo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Perfil implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
