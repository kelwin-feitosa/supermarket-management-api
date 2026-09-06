package com.exemplo.meu_primeiro_projeto.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados retornados após uma autenticação bem-sucedida.")
public record LoginResponse(

    @Schema(
        description = "Token JWT utilizado para autenticar as requisições.",
        example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    String token

) {
}