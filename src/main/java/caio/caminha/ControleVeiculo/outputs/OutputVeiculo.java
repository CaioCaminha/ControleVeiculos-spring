package caio.caminha.ControleVeiculo.outputs;

import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.models.Veiculo;
import org.springframework.data.domain.Page;

public class OutputVeiculo {
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

    public OutputVeiculo(){}

    public OutputVeiculo(Veiculo veiculo){
        this.id = veiculo.getId();
        this.tipo = veiculo.getTipo();
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.ano = veiculo.getAno();
        this.valor = veiculo.getValor();
        this.combustivel = veiculo.getCombustivel();
        this.usuario = veiculo.getUsuario().getEmail();
        this.diaRodizio = veiculo.getDiaRodizio();
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

    public String getCombustivel() {
        return combustivel;
    }

    public String getDiaRodizio() {
        return diaRodizio;
    }

    public boolean isRodizioAtivo() {
        return rodizioAtivo;
    }

    public void setRodizioAtivo(boolean rodizioAtivo) {
        this.rodizioAtivo = rodizioAtivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario.getEmail();
    }

    public static Page<OutputVeiculo> convert(Page<Veiculo> veiculos){
        return veiculos.map(OutputVeiculo::new);
    }
}
