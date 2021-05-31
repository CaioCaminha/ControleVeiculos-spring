package caio.caminha.ControleVeiculo.feignRequests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Modelo {
    @JsonProperty("modelos")
    private List<ObjectFipe> modelos;
    @JsonProperty("anos")
    private List<ObjectFipe> anos;

    public List<ObjectFipe> getModelos() {
        return modelos;
    }

    public void setModelos(List<ObjectFipe> modelos) {
        this.modelos = modelos;
    }
}
