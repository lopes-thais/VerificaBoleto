package com.thais.verificaBoleto.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().info(new Info()
                .title("Boleto Verify API")
                .version("1.0")
                .description("API para validação de coerência de boletos bancários via linha digitável, "
                        + "usando os módulos 10 e 11 e extração dos dados cadastrados na linha digitável.")
        );

    }
}
