package caio.caminha.ControleVeiculo.controllers;

import caio.caminha.ControleVeiculo.exceptions.UsuarioInvalidoException;
import caio.caminha.ControleVeiculo.inputs.AuthInput;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.outputs.OutputToken;
import caio.caminha.ControleVeiculo.securityServices.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthInput input){
        UsernamePasswordAuthenticationToken dadosLogin = input.convert();
               try{
                   Authentication authentication = authenticationManager.authenticate(dadosLogin);
                   String token = this.tokenService.generateToken(authentication);
                   return ResponseEntity.ok(new OutputToken(token, "Bearer "));
               }catch(Exception e){
                   return ResponseEntity.badRequest().body("Usuário Inválido, tente novamente!");
               }
    }
}
