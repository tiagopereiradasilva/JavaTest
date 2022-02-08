package br.com.sigabem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
		@Info(title = "Siga Bem - API",
				description = "Api para gerar cotação (valor e tempo de entrega) " +
						"a partir de cep de origem e cep de destino.",
				version = "1.0",
				contact = @Contact(
						name = "Tiago Pereira da Silva",
						email = "tiagopereiracx@gmail.com",
						url = "https://github.com/tiagopereiradasilva"
				)
		))
public class SigaBemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigaBemApplication.class, args);
	}

}
