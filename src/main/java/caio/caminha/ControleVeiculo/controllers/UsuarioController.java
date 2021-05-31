package caio.caminha.ControleVeiculo.controllers;

import caio.caminha.ControleVeiculo.inputs.InputUsuario;
import caio.caminha.ControleVeiculo.outputs.OutputUsuario;
import caio.caminha.ControleVeiculo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

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
