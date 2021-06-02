package caio.caminha.ControleVeiculo.services;

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
public class CarroService extends VeiculoClient{

    @Autowired
    private FipeClient fipeClient;

    public CarroService(FipeClient fipeClient) {
        super(fipeClient);
    }

    public void montaCarro(Veiculo veiculo, InputVeiculo input, Usuario usuario){
        int codigoMarcaCarro = this.getCodigoMarcaCarro(input.getMarca());
        int codigoModeloCarro = this.getCodigoModeloCarro(input.getModelo(), codigoMarcaCarro);
        int ano = Integer.parseInt(input.getAno());

        veiculo.setUsuario(usuario);
        this.verificaCombustivel(veiculo, input, codigoMarcaCarro, codigoModeloCarro, ano);

    }

    private int getCodigoMarcaCarro(String nomeMarca){
        return super.getCodigoMarca(nomeMarca, "carros");
    }

    private int getCodigoModeloCarro(String nomeModelo, int codigoMarca){
        return super.getCodigoModelo(nomeModelo, codigoMarca, "caminhoes");
    }

    private String diaDoRodizio(String ano){
        if(ano.substring(3).equals("0") || ano.substring(3).equals("1")){
            return "segunda-feira";
        }
        if(ano.substring(3).equals("2") || ano.substring(3).equals("3")){
            return "terça-feira";
        }
        if(ano.substring(3).equals("4") || ano.substring(3).equals("5")){
            return "quarta-feira";
        }
        if(ano.substring(3).equals("6") || ano.substring(3).equals("7")){
            return "quinta-feira";
        }
        if (ano.substring(3).equals("8") || ano.substring(3).equals("9")){
            return "sexta-feira";
        }else{
            return "sabado-feira";
        }
    }

    private void verificaCombustivel(Veiculo veiculo,
                                     InputVeiculo input,
                                     int codigoMarcaCarro,
                                     int codigoModeloCarro,
                                     int ano){
        VeiculoFipe veiculoFipe;
        if(input.getCombustivel().equals("Gasolina")){
            veiculoFipe = this.getVeiculoFipeCarroGasolina(codigoMarcaCarro, codigoModeloCarro, ano);
        }else {
            veiculoFipe = this.getVeiculoFipeCarroDiesel(codigoMarcaCarro, codigoModeloCarro, ano);
        }
        veiculo.setValor(veiculoFipe.getValor());
        veiculo.setDiaRodizio(this.diaDoRodizio(input.getAno()));
    }


    private VeiculoFipe getVeiculoFipeCarroGasolina(int codigoMarca, int codigoModelo, int ano){
        return this.fipeClient.getVeiculoFipeCarroGasolina(codigoMarca, codigoModelo, ano);
    }

    private VeiculoFipe getVeiculoFipeCarroDiesel(int codigoMarca, int codigoModelo, int ano){
        return this.fipeClient.getVeiculoFipeCarroDiesel(codigoMarca, codigoModelo, ano);
    }

}
