package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.inputs.InputVeiculo;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.models.Veiculo;
import caio.caminha.ControleVeiculo.outputs.OutputVeiculo;
import caio.caminha.ControleVeiculo.repositories.UsuarioRepository;
import caio.caminha.ControleVeiculo.repositories.VeiculoRepository;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.Calendar;

@Service
public class VeiculoService {


    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarroService carroService;

    @Autowired
    private MotoService motoService;

    @Autowired
    private CaminhaoService caminhaoService;

    @Autowired
    private TokenService tokenService;

    public Page<OutputVeiculo> getVeiculos(String token, Pageable pageable){
        Long id = this.tokenService.getUserId(token.substring(7, token.length()));
        String userName = this.usuarioRepository.getById(id).getEmail();
        Page<Veiculo> veiculos = this.veiculoRepository.findByUsuarioEmail(userName, pageable);
        return OutputVeiculo.convert(veiculos);
    }

    public OutputVeiculo saveVeiculo(InputVeiculo input, Usuario usuario){
            switch (input.getTipo()){
                case "moto":
                    return saveMoto(input, usuario);
                case "caminhao":
                    return saveCaminhao(input, usuario);
                default:
                    return saveCarro(input, usuario);
            }
    }


    public OutputVeiculo saveCarro(InputVeiculo input, Usuario usuario){
        Veiculo veiculo = input.convert();

        //Realiza as requisições do feign e retorna o obj veiculo montado
        this.carroService.montaCarro(veiculo, input, usuario);
        this.veiculoRepository.save(veiculo);
        Calendar cal = Calendar.getInstance();
        OutputVeiculo output = new OutputVeiculo(veiculo);
        output.setRodizioAtivo(output.getDiaRodizio().equals(this.weekDay(cal)));
        return output;

    }

    public OutputVeiculo saveMoto(InputVeiculo input, Usuario usuario){
        Veiculo veiculo = input.convert();

        this.motoService.montaMoto(veiculo, input, usuario);

        this.veiculoRepository.save(veiculo);
        Calendar cal = Calendar.getInstance();
        OutputVeiculo output = new OutputVeiculo(veiculo);
        output.setRodizioAtivo(output.getDiaRodizio().equals(this.weekDay(cal)));
        return output;

    }

    public OutputVeiculo saveCaminhao(InputVeiculo input, Usuario usuario){
        Veiculo veiculo = input.convert();

        this.caminhaoService.montaCaminhao(veiculo, input, usuario);

        this.veiculoRepository.save(veiculo);
        Calendar cal = Calendar.getInstance();
        OutputVeiculo output = new OutputVeiculo(veiculo);
        output.setRodizioAtivo(output.getDiaRodizio().equals(this.weekDay(cal)));
        return output;

    }

    public String weekDay(Calendar cal) {
        return new DateFormatSymbols().getWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
    }


}
