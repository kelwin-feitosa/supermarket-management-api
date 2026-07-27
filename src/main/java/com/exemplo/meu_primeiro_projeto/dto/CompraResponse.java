package com.exemplo.meu_primeiro_projeto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record CompraResponse(

    @Schema(description = "Identificador da compra", example = "1")
    Long id,

    @Schema(description = "Identificador do fornecedor responsável pela compra", example = "3")
    Long fornecedorId,

    @Schema(description = "Data e horário em que a compra foi realizada", example = "2026-07-27T14:30:00")
    LocalDateTime dataCompra,

    @Schema(description = "Valor total da compra", example = "2500.00")
    BigDecimal valorTotal,

    @Schema(description = "Lista de produtos adquiridos na compra")
    List<ItemCompraResponse> itens

) {
}