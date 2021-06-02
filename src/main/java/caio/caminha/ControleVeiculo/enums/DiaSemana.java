package caio.caminha.ControleVeiculo.enums;

public enum DiaSemana {
    SEGUNDA("segunda-feira"),
    TERCA("terça-feira"),
    QUARTA("quarta-feira"),
    QUINTA("quinta-feira"),
    SEXTA("sexta-feira");

    private final String diaDaSemana;

    DiaSemana(String s) {
        this.diaDaSemana = s;
    }

    public String getDiaDaSemana(){
        return this.diaDaSemana;
    }
}
