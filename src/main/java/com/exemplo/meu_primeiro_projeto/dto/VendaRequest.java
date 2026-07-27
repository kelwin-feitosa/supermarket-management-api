package com.exemplo.meu_primeiro_projeto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados necessários para realizar uma venda.")
public record VendaRequest(

    @Schema(
        description = "Identificador do cliente responsável pela venda.",
        example = "1"
    )
    @NotNull(message = "O cliente é obrigatório.")
    Long clienteId

) {
}