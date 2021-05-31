package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.feignRequests.FipeRequest;
import caio.caminha.ControleVeiculo.feignRequests.Modelo;
import caio.caminha.ControleVeiculo.feignRequests.ObjectFipe;
import caio.caminha.ControleVeiculo.feignRequests.VeiculoFipe;
import caio.caminha.ControleVeiculo.inputs.InputVeiculo;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.models.Veiculo;
import caio.caminha.ControleVeiculo.outputs.OutputVeiculo;
import caio.caminha.ControleVeiculo.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private FipeRequest fipeRequest;

    @Autowired
    private VeiculoRepository repository;





    public OutputVeiculo saveVeiculo(InputVeiculo input, Usuario usuario){
            switch (input.getTipo()){
                case "carro":
                    return saveCarro(input, usuario);
                case "moto":
                    return saveMoto(input, usuario);
                case "caminhao":
                    return saveCaminhao(input, usuario);
                default:
                    return saveCarro(input, usuario);
            }
    }


    public OutputVeiculo saveCarro(InputVeiculo input, Usuario usuario){
        int codigoMarcaCarro = this.getCodigoMarcaCarro(input.getMarca());
        int codigoModeloCarro = this.getCodigoModeloCarro(input.getModelo(), codigoMarcaCarro);
        int ano = Integer.parseInt(input.getAno());
        Veiculo veiculo = input.convert();
        veiculo.setUsuario(usuario);
        if(input.getCombustivel().equals("Gasolina")){
            VeiculoFipe veiculoFipe = this.getVeiculoFipeCarroGasolina(codigoMarcaCarro, codigoModeloCarro, ano);
            veiculo.setValor(veiculoFipe.getValor());
            veiculo.setDiaRodizio(this.diaDoRodizio(input.getAno()));
        }else {
            VeiculoFipe veiculoFipe = this.getVeiculoFipeCarroDiesel(codigoMarcaCarro, codigoModeloCarro, ano);
            veiculo.setValor(veiculoFipe.getValor());
            veiculo.setDiaRodizio(this.diaDoRodizio(input.getAno()));
        }
        this.repository.save(veiculo);
        return new OutputVeiculo(veiculo);

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

    public OutputVeiculo saveMoto(InputVeiculo input, Usuario usuario){
        int codigoMarcaMoto = this.getCodigoMarcaMoto(input.getMarca());
        int codigoModeloMoto = this.getCodigoModeloMoto(input.getModelo(), codigoMarcaMoto);
        int ano = Integer.parseInt(input.getAno());
        VeiculoFipe veiculoFipe = this.getVeiculoFipeMoto(codigoMarcaMoto, codigoModeloMoto, ano);
        Veiculo veiculo = input.convert();
        veiculo.setUsuario(usuario);
        veiculo.setValor(veiculoFipe.getValor());
        veiculo.setDiaRodizio(this.diaDoRodizio(input.getAno()));
        this.repository.save(veiculo);
        return new OutputVeiculo(veiculo);

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
    public OutputVeiculo saveCaminhao(InputVeiculo input, Usuario usuario){
        int codigoMarcaMoto = this.getCodigoMarcaCaminhao(input.getMarca());
        int codigoModeloMoto = this.getCodigoModeloCaminhao(input.getModelo(), codigoMarcaMoto);
        Integer ano = Integer.parseInt(input.getAno());
        VeiculoFipe veiculoFipe = this.getVeiculoFipeCaminhao(codigoMarcaMoto, codigoModeloMoto, ano);
        Veiculo veiculo = input.convert();
        veiculo.setUsuario(usuario);
        veiculo.setValor(veiculoFipe.getValor());
        veiculo.setDiaRodizio(this.diaDoRodizio(ano.toString()));
        this.repository.save(veiculo);
        return new OutputVeiculo(veiculo);

    }

    private int getCodigoMarcaCaminhao(String nomeMarca){
        ArrayList<ObjectFipe> marcas =  this.fipeRequest.getMarcasCaminhoes();
        for (ObjectFipe marca:marcas){
            if(marca.getNome().equals(nomeMarca)){
                return Integer.parseInt(marca.getCodigo());
            }
        }
        return 0;
    }

    private int getCodigoModeloCaminhao(String nomeModelo, int codigoMarca){
        Modelo modelos =  this.fipeRequest.getModelosCaminhoes(codigoMarca);

        List<ObjectFipe> modelo = modelos.getModelos();

        for(ObjectFipe modeloObject : modelo){
            if(modeloObject.getNome().equals(nomeModelo)){
                return Integer.parseInt(modeloObject.getCodigo());
            }
        }
        return 0;
    }

    private VeiculoFipe getVeiculoFipeMoto(int codigoMarca, int codigoModelo, int ano){
        return this.fipeRequest.getVeiculoFipeMoto(codigoMarca, codigoModelo, ano);
    }

    private VeiculoFipe getVeiculoFipeCarroGasolina(int codigoMarca, int codigoModelo, int ano){
        return this.fipeRequest.getVeiculoFipeCarroGasolina(codigoMarca, codigoModelo, ano);
    }

    private VeiculoFipe getVeiculoFipeCarroDiesel(int codigoMarca, int codigoModelo, int ano){
        return this.fipeRequest.getVeiculoFipeCarroDiesel(codigoMarca, codigoModelo, ano);
    }

    private VeiculoFipe getVeiculoFipeCaminhao(int codigoMarca, int codigoModelo, int ano){
        return this.fipeRequest.getVeiculoFipeCaminhoes(codigoMarca, codigoModelo, ano);
    }

    private String diaDoRodizio(String ano){
        if(ano.substring(3).equals("0") || ano.substring(3).equals("1")){
            return "segunda";
        }
        if(ano.substring(3).equals("2") || ano.substring(3).equals("3")){
            return "terça";
        }
        if(ano.substring(3).equals("4") || ano.substring(3).equals("5")){
            return "quarta";
        }
        if(ano.substring(3).equals("6") || ano.substring(3).equals("7")){
            return "quinta";
        }
        if (ano.substring(3).equals("8") || ano.substring(3).equals("9")){
            return "sexta";
        }else{
            return "sabado";
        }
    }

    public String weekDay(Calendar cal) {
        return new DateFormatSymbols().getWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
    }


}
