package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.feignRequests.FipeRequest;
import caio.caminha.ControleVeiculo.feignRequests.Modelo;
import caio.caminha.ControleVeiculo.feignRequests.ObjectFipe;
import caio.caminha.ControleVeiculo.feignRequests.VeiculoFipe;
import caio.caminha.ControleVeiculo.inputs.InputVeiculo;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.models.Veiculo;
import caio.caminha.ControleVeiculo.outputs.OutputVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarroService {

    @Autowired
    private FipeRequest fipeRequest;

    public void montaCarro(Veiculo veiculo, InputVeiculo input, Usuario usuario){
        int codigoMarcaCarro = this.getCodigoMarcaCarro(input.getMarca());
        int codigoModeloCarro = this.getCodigoModeloCarro(input.getModelo(), codigoMarcaCarro);
        int ano = Integer.parseInt(input.getAno());

        veiculo.setUsuario(usuario);
        this.verificaCombustivel(veiculo, input, codigoMarcaCarro, codigoModeloCarro, ano);

    }

    private int getCodigoMarcaCarro(String nomeMarca){
        ArrayList<ObjectFipe> marcas =  this.fipeRequest.getMarcasCarros();
        for (ObjectFipe marca:marcas){
            if(marca.getNome().equals(nomeMarca)){
                return Integer.parseInt(marca.getCodigo());
            }
        }
        return 0;
    }

    private int getCodigoModeloCarro(String nomeModelo, int codigoMarca){
        Modelo modelos =  this.fipeRequest.getModelosCarros(codigoMarca);

        List<ObjectFipe> modelo = modelos.getModelos();

        for(ObjectFipe modeloObject : modelo){
            if(modeloObject.getNome().equals(nomeModelo)){
                return Integer.parseInt(modeloObject.getCodigo());
            }
        }
        return 0;
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
        return this.fipeRequest.getVeiculoFipeCarroGasolina(codigoMarca, codigoModelo, ano);
    }

    private VeiculoFipe getVeiculoFipeCarroDiesel(int codigoMarca, int codigoModelo, int ano){
        return this.fipeRequest.getVeiculoFipeCarroDiesel(codigoMarca, codigoModelo, ano);
    }

}
