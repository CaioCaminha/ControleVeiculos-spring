package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.exceptions.UsuarioInvalidoException;
import caio.caminha.ControleVeiculo.inputs.InputUsuario;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.outputs.OutputUsuario;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public OutputUsuario save(InputUsuario input) throws Exception {
        Usuario usuario = input.convert();
        if(!this.usuarioRepository.findByEmail(usuario.getEmail()).isPresent() &&
                !this.usuarioRepository.findByCpf(usuario.getCpf()).isPresent() ){

            this.usuarioRepository.save(usuario);
            return new OutputUsuario(usuario);
        }
        throw new UsuarioInvalidoException("Email ou CPF inválidos, tente novamente!");
    }

}
