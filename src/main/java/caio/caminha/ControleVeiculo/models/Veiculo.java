package caio.caminha.ControleVeiculo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "veiculo")
public class Veiculo {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tipo; //carro/moto/caminhão
    private String marca;
    private String modelo;
    private Integer ano;
    private String valor;
    private String diaRodizio;
    private String combustivel;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    public Veiculo(){}

    public Veiculo(String marca, Integer ano, String modelo, String tipo, String combustivel){
        this.marca = marca;
        this.ano = ano;
        this.modelo = modelo;
        this.tipo = tipo;
        this.combustivel = combustivel;
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

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDiaRodizio() {
        return diaRodizio;
    }

    public void setDiaRodizio(String diaRodizio) {
        this.diaRodizio = diaRodizio;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
