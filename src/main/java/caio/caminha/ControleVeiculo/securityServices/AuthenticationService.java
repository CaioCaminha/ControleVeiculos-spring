package caio.caminha.ControleVeiculo.securityServices;

import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByCpf(username);

        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UsernameNotFoundException("username inválido");
    }
}
