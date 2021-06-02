package caio.caminha.ControleVeiculo.outputs;

public class OutputToken {
    private String token;
    private String type;

    public OutputToken(String token, String type){
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
