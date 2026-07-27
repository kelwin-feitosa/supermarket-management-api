package com.exemplo.meu_primeiro_projeto.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public record ItemCompraResponse(

    @Schema(description = "Identificador do item da compra", example = "1")
    Long id,

    @Schema(description = "Identificador do produto comprado", example = "5")
    Long produtoId,

    @Schema(description = "Nome do produto comprado", example = "Café 500g")
    String nomeProduto,

    @Schema(description = "Quantidade comprada", example = "50")
    Integer quantidade,

    @Schema(description = "Preço de compra unitário", example = "12.00")
    BigDecimal precoCompra,

    @Schema(description = "Valor total do item comprado", example = "600.00")
    BigDecimal subtotal

) {
}