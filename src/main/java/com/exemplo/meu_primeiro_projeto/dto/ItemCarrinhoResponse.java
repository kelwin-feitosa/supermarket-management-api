package com.exemplo.meu_primeiro_projeto.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public record ItemCarrinhoResponse(

    @Schema(description = "Identificador do item do carrinho", example = "1")
    Long id,

    @Schema(description = "Identificador do produto", example = "10")
    Long produtoId,

    @Schema(description = "Nome do produto adicionado ao carrinho", example = "Refrigerante 2L")
    String nomeProduto,

    @Schema(description = "Quantidade do produto no carrinho", example = "3")
    Integer quantidade,

    @Schema(description = "Preço unitário do produto no momento da adição", example = "8.50")
    BigDecimal precoUnitario,

    @Schema(description = "Valor total do item (quantidade × preço unitário)", example = "25.50")
    BigDecimal subtotal

) {
}