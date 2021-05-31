package caio.caminha.ControleVeiculo.inputs;

import caio.caminha.ControleVeiculo.models.Veiculo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
public class InputVeiculo {
    @NotNull @NotEmpty
    private String tipo;
    @NotNull @NotEmpty
    private String marca;
    @NotNull @NotEmpty
    private String modelo;
    @NotNull @NotEmpty
    private String ano;
    @NotNull @NotEmpty
    private String combustivel;

    public Veiculo convert(){
        return new Veiculo(this.marca, Integer.parseInt(this.ano), this.modelo, this.tipo, this.combustivel);
    }

    public String getMarca() {
        return marca;
    }

    public String getTipo() {
        return tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public String getAno() {
        return ano;
    }

    public String getCombustivel() {
        return combustivel;
    }
}
