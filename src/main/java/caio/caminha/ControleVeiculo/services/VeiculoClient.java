package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.feignRequests.FipeClient;
import caio.caminha.ControleVeiculo.feignRequests.Modelo;
import caio.caminha.ControleVeiculo.feignRequests.ObjectFipe;

import java.util.ArrayList;
import java.util.List;

public class VeiculoClient {

    private FipeClient fipeClient;

    public VeiculoClient(FipeClient fipeClient){
        this.fipeClient = fipeClient;
    }

    public int getCodigoMarca(String nomeMarca, String tipo){
        ArrayList<ObjectFipe> marcas =  this.fipeClient.getMarcas(tipo);
        for(ObjectFipe marca:marcas){
            if(marca.getNome().equals(nomeMarca)){
                return Integer.parseInt(marca.getCodigo());
            }
        }
        return 0;
    }

    public int getCodigoModelo(String nomeModelo, int codigoMarca, String tipo){
        Modelo modelos =  this.fipeClient.getModelos(codigoMarca, tipo);

        List<ObjectFipe> modelo = modelos.getModelos();

        for(ObjectFipe modeloObject : modelo){
            if(modeloObject.getNome().equals(nomeModelo)){
                return Integer.parseInt(modeloObject.getCodigo());
            }
        }
        return 0;
    }


}
