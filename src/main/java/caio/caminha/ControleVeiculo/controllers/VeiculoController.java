package caio.caminha.ControleVeiculo.controllers;

import caio.caminha.ControleVeiculo.exceptions.VeiculoInvalidoException;
import caio.caminha.ControleVeiculo.inputs.InputVeiculo;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.outputs.OutputVeiculo;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import caio.caminha.ControleVeiculo.repositories.VeiculoRepository;
import caio.caminha.ControleVeiculo.securityServices.TokenService;
import caio.caminha.ControleVeiculo.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    public static final int POSICAO_BEARER = 7;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private TokenService tokenService;



    @PostMapping
    public ResponseEntity<?> createVeiculo(@RequestBody InputVeiculo input,
                                                       UriComponentsBuilder builder,
                                                       @RequestHeader("Authorization") String token){
        try{
                Long id = this.tokenService.getUserId(token.substring(POSICAO_BEARER, token.length()));
                Usuario usuario = this.usuarioRepository.getById(id);

                OutputVeiculo output = this.veiculoService.saveVeiculo(input, usuario);
                URI uri = builder.path("/veiculos/{id}").buildAndExpand(output.getId()).toUri();

                return ResponseEntity.created(uri).body(output);
        }catch(Exception e){
            return ResponseEntity.badRequest()
                    .body(new VeiculoInvalidoException("Erro ao Cadastrar veículo, verifique os campos!").getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listVeiculo(@RequestHeader("Authorization") String token,
                                                           @PageableDefault(sort = "id",
                                                                   direction = Sort.Direction.ASC,
                                                                   page = 0, size = 10)Pageable pageable){
        if(this.tokenService.isTokenValid(token.substring(7, token.length()))){
            Page<OutputVeiculo> outputVeiculos = this.veiculoService.getVeiculos(token, pageable);
            return ResponseEntity.ok(outputVeiculos);
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Autentique-se e tente novamente!");
        }

    }



}
