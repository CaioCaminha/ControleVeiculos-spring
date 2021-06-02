package caio.caminha.ControleVeiculo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableFeignClients
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableSwagger2
public class ControleVeiculoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleVeiculoApplication.class, args);
	}

}
