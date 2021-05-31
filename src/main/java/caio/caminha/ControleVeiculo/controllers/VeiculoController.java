package caio.caminha.ControleVeiculo.controllers;

import caio.caminha.ControleVeiculo.inputs.InputVeiculo;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.models.Veiculo;
import caio.caminha.ControleVeiculo.outputs.OutputVeiculo;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import caio.caminha.ControleVeiculo.repositories.VeiculoRepository;
import caio.caminha.ControleVeiculo.services.TokenService;
import caio.caminha.ControleVeiculo.services.VeiculoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Calendar;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<OutputVeiculo> createVeiculo(@RequestBody InputVeiculo input,
                                                       UriComponentsBuilder builder,
                                                       @RequestHeader("Authorization") String token){
        try{
            Long id = this.tokenService.getUserId(token.substring(7, token.length()));
            Usuario usuario = this.usuarioRepository.getById(id);
            OutputVeiculo output = this.veiculoService.saveVeiculo(input, usuario);

            URI uri = builder.path("/veiculos/{id}").buildAndExpand(output.getId()).toUri();
            return ResponseEntity.created(uri).body(output);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<OutputVeiculo>> listVeiculo(@RequestHeader("Authorization") String token,
                                                           @PageableDefault(sort = "id",
                                                                   direction = Sort.Direction.ASC,
                                                                   page = 0, size = 10)Pageable pageable){
        if(this.tokenService.isTokenValid(token.substring(7, token.length()))){
            Page<OutputVeiculo> outputVeiculos = this.veiculoService.getVeiculos(token, pageable);
            return ResponseEntity.ok(outputVeiculos);
        }else{
            return ResponseEntity.badRequest().build();
        }

    }



}
