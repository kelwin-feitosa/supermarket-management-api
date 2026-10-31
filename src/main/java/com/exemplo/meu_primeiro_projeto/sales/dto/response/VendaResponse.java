package com.exemplo.meu_primeiro_projeto.sales.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record VendaResponse(

    @Schema(description = "Identificador da venda", example = "1")
    Long id,

    @Schema(description = "Identificador do usuario que realizou a compra", example = "5")
    Long usuarioId,

    @Schema(description = "Data e horário em que a venda foi realizada", example = "2026-07-27T15:45:00")
    OffsetDateTime dataVenda,

    @Schema(description = "Valor total da venda", example = "180.50")
    BigDecimal valorTotal,

    @Schema(description = "Lista de produtos vendidos")
    List<ItemVendaResponse> itens

) {
}