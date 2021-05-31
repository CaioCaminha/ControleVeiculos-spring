package caio.caminha.ControleVeiculo.outputs;

import caio.caminha.ControleVeiculo.models.Usuario;
import caio.caminha.ControleVeiculo.models.Veiculo;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

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
        this.diaRodizio = veiculo.getDiaRodizio();
        this.combustivel = veiculo.getCombustivel();
        this.usuario = veiculo.getUsuario().getEmail();
    }

    public void setRodizioAtivo(boolean rodizioAtivo) {
        this.rodizioAtivo = rodizioAtivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDiaRodizio() {
        return diaRodizio;
    }

    public void setDiaRodizio(String diaRodizio) {
        this.diaRodizio = diaRodizio;
    }

    public boolean isRodizioAtivo() {
        return rodizioAtivo;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
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
