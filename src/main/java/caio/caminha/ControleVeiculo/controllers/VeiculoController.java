package caio.caminha.ControleVeiculo.controllers;

import caio.caminha.ControleVeiculo.inputs.InputVeiculo;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.models.Veiculo;
import caio.caminha.ControleVeiculo.outputs.OutputVeiculo;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import caio.caminha.ControleVeiculo.repositories.VeiculoRepository;
import caio.caminha.ControleVeiculo.services.TokenService;
import caio.caminha.ControleVeiculo.services.VeiculoService;
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
        Long id = this.tokenService.getUserId(token.substring(7, token.length()));
        Usuario usuario = this.usuarioRepository.getById(id);
        OutputVeiculo output = this.veiculoService.saveVeiculo(input, usuario);
        Calendar cal = Calendar.getInstance();
        output.setRodizioAtivo(output.getDiaRodizio().equals(this.veiculoService.weekDay(cal)));
        output.setDiaRodizio(this.veiculoService.weekDay(cal));
        URI uri = builder.path("/veiculos/{id}").buildAndExpand(output.getId()).toUri();
        return ResponseEntity.created(uri).body(output);
    }

    @GetMapping
    public ResponseEntity<Page<OutputVeiculo>> listVeiculo(@RequestHeader("Authorization") String token,
                                                           @PageableDefault(sort = "id",
                                                                   direction = Sort.Direction.ASC,
                                                                   page = 0, size = 10)Pageable pageable){
        if(this.tokenService.isTokenValid(token.substring(7, token.length()))){
            Long id = this.tokenService.getUserId(token.substring(7, token.length()));
            String userName = this.usuarioRepository.getById(id).getEmail();
            Page<Veiculo> veiculos = this.veiculoRepository.findByUsuarioEmail(userName, pageable);
            return ResponseEntity.ok(OutputVeiculo.convert(veiculos));
        }else{
            return ResponseEntity.badRequest().build();
        }

    }



}
