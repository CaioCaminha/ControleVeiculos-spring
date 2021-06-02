package caio.caminha.ControleVeiculo.enums;

public enum TipoVeiculo {
    CARRO("carros"),
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
