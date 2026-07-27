package com.exemplo.meu_primeiro_projeto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("API de Gerenciamento de Supermercado")
                        .description("""
                                API REST para gerenciamento de operações comerciais de supermercado,
                                incluindo cadastro de produtos, categorias, clientes, fornecedores,
                                controle de carrinho, compras e vendas.

                                Desenvolvida utilizando Java 21, Spring Boot, Spring Data JPA,
                                Hibernate e PostgreSQL.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                    .name("Kelwin Feitosa")
                                    .email("kelwinfeitosa@gmail.com")
                                    .url("https://github.com/kelwin-feitosa"))
                        .license(new License()
                                    .name("MIT"))
                        );
    }
}
