package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.inputs.InputUsuario;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.outputs.OutputUsuario;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public OutputUsuario save(InputUsuario input) throws ParseException {
        Usuario usuario = input.convert();
        //todo: criar verificar de email
        this.repository.save(usuario);
        return new OutputUsuario(usuario);
    }

}
