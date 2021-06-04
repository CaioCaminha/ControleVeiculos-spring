package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.enums.DiaSemana;
import caio.caminha.ControleVeiculo.enums.TipoVeiculo;
import caio.caminha.ControleVeiculo.feignRequests.FipeClient;
import caio.caminha.ControleVeiculo.feignRequests.Modelo;
import caio.caminha.ControleVeiculo.feignRequests.ObjectFipe;
import caio.caminha.ControleVeiculo.feignRequests.VeiculoFipe;
import caio.caminha.ControleVeiculo.inputs.InputVeiculo;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.models.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaminhaoService extends VeiculoClient{

    @Autowired
    private FipeClient fipeClient;

    public CaminhaoService(FipeClient fipeClient) {
        super(fipeClient);
    }

    public void montaCaminhao(Veiculo veiculo, InputVeiculo input, Usuario usuario){
        int codigoMarcaMoto = this.getCodigoMarcaCaminhao(input.getMarca());
        int codigoModeloMoto = this.getCodigoModeloCaminhao(input.getModelo(), codigoMarcaMoto);
        int ano = Integer.parseInt(input.getAno());

        VeiculoFipe veiculoFipe = this.getVeiculoFipeCaminhao(codigoMarcaMoto, codigoModeloMoto, ano);

        veiculo.setUsuario(usuario);
        veiculo.setValor(veiculoFipe.getValor());
        veiculo.setDiaRodizio(this.diaDoRodizio(input.getAno()));
    }

    private String diaDoRodizio(String ano){
        if(ano.substring(3).equals("0") || ano.substring(3).equals("1")){
            return DiaSemana.SEGUNDA.getDiaDaSemana();
        }
        if(ano.substring(3).equals("2") || ano.substring(3).equals("3")){
            return DiaSemana.TERCA.getDiaDaSemana();
        }
        if(ano.substring(3).equals("4") || ano.substring(3).equals("5")){
            return DiaSemana.QUARTA.getDiaDaSemana();
        }
        if(ano.substring(3).equals("6") || ano.substring(3).equals("7")){
            return DiaSemana.QUINTA.getDiaDaSemana();
        }
        if (ano.substring(3).equals("8") || ano.substring(3).equals("9")){
            return DiaSemana.SEXTA.getDiaDaSemana();
        }else{
            return "segunda-feira";
        }
    }

    private int getCodigoMarcaCaminhao(String nomeMarca){
        return super.getCodigoMarca(nomeMarca, TipoVeiculo.CAMINHAO.getTipo());
    }

    private int getCodigoModeloCaminhao(String nomeModelo, int codigoMarca){
        return super.getCodigoModelo(nomeModelo, codigoMarca, TipoVeiculo.CAMINHAO.getTipo());
    }

    private VeiculoFipe getVeiculoFipeCaminhao(int codigoMarca, int codigoModelo, int ano){
        return this.fipeClient.getVeiculoFipeCaminhoes(codigoMarca, codigoModelo, ano);
    }

}
