package caio.caminha.ControleVeiculo.controllers;

import caio.caminha.ControleVeiculo.inputs.InputUsuario;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.outputs.OutputUsuario;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import caio.caminha.ControleVeiculo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;
    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public List<Usuario> getUsuarios(){
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<OutputUsuario> createUsuario(@RequestBody @Valid InputUsuario input, UriComponentsBuilder builder){
        try{
            OutputUsuario output = this.service.save(input);
            URI uri = builder.path("/usuarios/{id}").buildAndExpand(output.getId()).toUri();
            output.setCpf(input.getCpf());
            return ResponseEntity.created(uri).body(output);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
