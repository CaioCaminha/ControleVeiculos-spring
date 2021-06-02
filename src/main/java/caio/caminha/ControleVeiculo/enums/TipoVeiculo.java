package caio.caminha.ControleVeiculo.enums;

public enum TipoVeiculo {
    CARROS("carros"),
    CARRO("carro"),
    MOTOS("motos"),
    MOTO("moto"),
    CAMINHAO("caminhoes");

    private String tipo;

    public String getTipo(){
        return this.tipo;
    }

    TipoVeiculo(String tipo) {
        this.tipo = tipo;
    }
}
