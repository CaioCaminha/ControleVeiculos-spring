package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.feignRequests.FipeRequest;
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
public class MotoService {

    @Autowired
    private FipeRequest fipeRequest;

    public void montaMoto(Veiculo veiculo, InputVeiculo input, Usuario usuario){
        int codigoMarcaMoto = this.getCodigoMarcaMoto(input.getMarca());
        int codigoModeloMoto = this.getCodigoModeloMoto(input.getModelo(), codigoMarcaMoto);
        int ano = Integer.parseInt(input.getAno());

        VeiculoFipe veiculoFipe = this.getVeiculoFipeMoto(codigoMarcaMoto, codigoModeloMoto, ano);

        veiculo.setUsuario(usuario);
        veiculo.setValor(veiculoFipe.getValor());
        veiculo.setDiaRodizio(this.diaDoRodizio(input.getAno()));
    }

    private int getCodigoMarcaMoto(String nomeMarca){
        ArrayList<ObjectFipe> marcas =  this.fipeRequest.getMarcasMotos();
        for (ObjectFipe marca:marcas){
            if(marca.getNome().equals(nomeMarca)){
                return Integer.parseInt(marca.getCodigo());
            }
        }
        return 0;
    }

    private int getCodigoModeloMoto(String nomeModelo, int codigoMarca){
        Modelo modelos =  this.fipeRequest.getModelosMotos(codigoMarca);

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
            return "sabado";
        }
    }

    private VeiculoFipe getVeiculoFipeMoto(int codigoMarca, int codigoModelo, int ano){
        return this.fipeRequest.getVeiculoFipeMoto(codigoMarca, codigoModelo, ano);
    }
}
