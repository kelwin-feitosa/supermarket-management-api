package com.exemplo.meu_primeiro_projeto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados necessários para adicionar ou alterar um item no carrinho.")
public record ItemCarrinhoRequest(

    @Schema(
        description = "Identificador do carrinho.",
        example = "1"
    )
    @NotNull(message = "O carrinho é obrigatório.")
    Long carrinhoId,


    @Schema(
        description = "Identificador do produto que será adicionado ao carrinho.",
        example = "10"
    )
    @NotNull(message = "O produto é obrigatório.")
    Long produtoId,


    @Schema(
        description = "Quantidade do produto no carrinho.",
        example = "2"
    )
    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    Integer quantidade

) {
}