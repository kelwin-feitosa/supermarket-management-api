package com.exemplo.meu_primeiro_projeto.sales.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public record CarrinhoResponse(

    @Schema(description = "Identificador do carrinho", example = "1")
    Long id,

    @Schema(description = "Valor total dos produtos presentes no carrinho", example = "150.00")
    BigDecimal valorTotal,

    @Schema(description = "Lista de produtos adicionados ao carrinho" )
    List<ItemCarrinhoResponse> itens

) {
}