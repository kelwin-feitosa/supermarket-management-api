package com.exemplo.meu_primeiro_projeto.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record VendaResponse(

    @Schema(description = "Identificador da venda", example = "1")
    Long id,

    @Schema(description = "Identificador do cliente que realizou a compra", example = "5")
    Long clienteId,

    @Schema(description = "Data e horário em que a venda foi realizada", example = "2026-07-27T15:45:00")
    LocalDateTime dataVenda,

    @Schema(description = "Valor total da venda", example = "180.50")
    BigDecimal valorTotal,

    @Schema(description = "Lista de produtos vendidos")
    List<ItemVendaResponse> itens

) {
}