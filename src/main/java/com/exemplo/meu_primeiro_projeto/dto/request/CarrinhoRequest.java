package com.exemplo.meu_primeiro_projeto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados necessários para criar um carrinho de compras.")
public record CarrinhoRequest(

    @Schema(
        description = "Identificador do cliente associado ao carrinho.",
        example = "1"
    )
    @NotNull(message = "O cliente é obrigatório.")
    Long clienteId

) {
}