package caio.caminha.ControleVeiculo.services;

import caio.caminha.ControleVeiculo.enums.DiaSemana;
import caio.caminha.ControleVeiculo.enums.TipoVeiculo;
import caio.caminha.ControleVeiculo.feignRequests.FipeClient;
import caio.caminha.ControleVeiculo.feignRequests.VeiculoFipe;
import caio.caminha.ControleVeiculo.inputs.InputVeiculo;
import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.models.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotoService extends VeiculoClient{
    @Autowired
    private FipeClient fipeClient;
    public MotoService(FipeClient fipeClient) {
        super(fipeClient);
    }

    public void montaMoto(Veiculo veiculo, InputVeiculo input, Usuario usuario){
        int codigoMarcaMoto = this.getCodigoMarcaMoto(input.getMarca());
        int codigoModeloMoto = this.getCodigoModeloMoto(input.getModelo(), codigoMarcaMoto);
        int ano = Integer.parseInt(input.getAno());

        VeiculoFipe veiculoFipe = this.getVeiculoFipeMoto(codigoMarcaMoto, codigoModeloMoto, ano);
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

    private int getCodigoMarcaMoto(String nomeMarca){
        return super.getCodigoMarca(nomeMarca, TipoVeiculo.MOTOS.getTipo());
    }

    private int getCodigoModeloMoto(String nomeModelo, int codigoMarca){
        return super.getCodigoModelo(nomeModelo, codigoMarca, TipoVeiculo.MOTOS.getTipo());
    }

    private VeiculoFipe getVeiculoFipeMoto(int codigoMarca, int codigoModelo, int ano){
        return this.fipeClient.getVeiculoFipeMoto(codigoMarca, codigoModelo, ano);
    }
}
