package com.exemplo.meu_primeiro_projeto.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public record ItemVendaResponse(

    @Schema(description = "Identificador do item da venda", example = "1")
    Long id,

    @Schema(description = "Identificador do produto vendido", example = "20")
    Long produtoId,

    @Schema(description = "Nome do produto vendido", example = "Chocolate")
    String nomeProduto,

    @Schema(description = "Quantidade vendida", example = "2")
    Integer quantidade,

    @Schema(description = "Preço unitário no momento da venda", example = "7.90")
    BigDecimal precoUnitario,

    @Schema(description = "Valor total do item vendido", example = "15.80")
    BigDecimal subtotal

) {
}