package caio.caminha.ControleVeiculo.feignRequests;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(url = "https://parallelum.com.br/fipe/api/v1/", name = "fipe")
public interface FipeRequest {

    @GetMapping("carros/marcas")
    ArrayList<ObjectFipe> getMarcasCarros();

    @GetMapping("carros/marcas/{codigoMarca}/modelos")
    Modelo getModelosCarros(@PathVariable("codigoMarca") int codigoMarca);

    @GetMapping("carros/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{ano}-1")
    VeiculoFipe getVeiculoFipeCarroGasolina(@PathVariable("codigoMarca") int codigoMarca,
                                            @PathVariable("codigoModelo") int codigoModelo,
                                            @PathVariable("ano") int ano);

    @GetMapping("carros/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{ano}-3")
    VeiculoFipe getVeiculoFipeCarroDiesel(@PathVariable("codigoMarca") int codigoMarca,
                                          @PathVariable("codigoModelo") int codigoModelo,
                                          @PathVariable("ano") int ano);


    @GetMapping("motos/marcas")
    ArrayList<ObjectFipe> getMarcasMotos();

    @GetMapping("motos/marcas/{codigoMarca}/modelos")
    Modelo getModelosMotos(@PathVariable("codigoMarca") int codigoMarca);

    @GetMapping("motos/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{ano}-1")
    VeiculoFipe getVeiculoFipeMoto(@PathVariable("codigoMarca") int codigoMarca,
                                   @PathVariable("codigoModelo") int codigoModelo,
                                   @PathVariable("ano") int ano);

    @GetMapping("caminhoes/marcas")
    ArrayList<ObjectFipe> getMarcasCaminhoes();

    @GetMapping("caminhoes/marcas/{codigoMarca}/modelos")
    Modelo getModelosCaminhoes(@PathVariable("codigoMarca") int codigoMarca);

    @GetMapping("caminhoes/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{ano}-3")
    VeiculoFipe getVeiculoFipeCaminhoes(@PathVariable("codigoMarca") int codigoMarca,
                                        @PathVariable("codigoModelo") int codigoModelo,
                                        @PathVariable("ano") int ano);
}