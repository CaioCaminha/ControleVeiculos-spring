package caio.caminha.ControleVeiculo.feignRequests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class VeiculoFipe {
    @JsonProperty("Valor")
    private String Valor;
    @JsonProperty("Marca")
    private String Marca;
    @JsonProperty("Modelo")
    private String Modelo;
    @JsonProperty("AnoModelo")
    private Integer AnoModelo;
    @JsonProperty("Combustivel")
    private String Combustivel;
    @JsonProperty("CodigoFipe")
    private String CodigoFipe;
    @JsonProperty("MesReferencia")
    private String MesReferencia;
    @JsonProperty("TipoVeiculo")
    private Integer TipoVeiculo;
    @JsonProperty("SiglaCombustivel")
    private String SiglaCombustivel;

    public VeiculoFipe(String valor,
                       String marca,
                       String modelo,
                       Integer anoModelo,
                       String combustivel,
                       String codigoFipe,
                       String mesReferencia,
                       Integer tipoVeiculo,
                       String siglaCombustivel){
        this.Valor = valor;
        this.Marca = marca;
        this.Modelo = modelo;
        this.AnoModelo = anoModelo;
        this.Combustivel = combustivel;
        this.CodigoFipe = codigoFipe;
        this.MesReferencia = mesReferencia;
        this.TipoVeiculo = tipoVeiculo;
        this.SiglaCombustivel = siglaCombustivel;
    }

    public String getValor() {
        return Valor;
    }
}
