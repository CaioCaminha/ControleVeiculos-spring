package caio.caminha.ControleVeiculo.outputs;

import caio.caminha.ControleVeiculo.models.Veiculo;
import org.springframework.data.domain.Page;

public class OutputVeiculoCarro {
    private Long id;
    private String tipo;
    private String marca;
    private String modelo;
    private Integer ano;
    private String valor;
    private String diaRodizio;
    private String combustivel;
    private String usuario;
    private boolean rodizioAtivo;


    public OutputVeiculoCarro(){}

    public OutputVeiculoCarro(Veiculo veiculo){
        this.id = veiculo.getId();
        this.tipo = veiculo.getTipo();
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.ano = veiculo.getAno();
        this.valor = veiculo.getValor();
        this.diaRodizio = veiculo.getDiaRodizio();
        this.combustivel = veiculo.getCombustivel();
        this.usuario = veiculo.getUsuario().getEmail();
    }

    public Long getId() {
        return id;
    }


    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public String getValor() {
        return valor;
    }

    public String getDiaRodizio() {
        return diaRodizio;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isRodizioAtivo() {
        return rodizioAtivo;
    }

    public void setRodizioAtivo(boolean rodizioAtivo) {
        this.rodizioAtivo = rodizioAtivo;
    }

    public static Page<OutputVeiculoCarro> convert(Page<Veiculo> veiculos){
        return veiculos.map(OutputVeiculoCarro::new);
    }

}

