package com.exemplo.meu_primeiro_projeto.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados necessários para registrar um item dentro de uma compra.")
public record ItemCompraRequest(

    @Schema(
        description = "Identificador do produto comprado.",
        example = "1"
    )
    @NotNull(message = "O produto é obrigatório.")
    Long produtoId,


    @Schema(
        description = "Quantidade adquirida do produto.",
        example = "50"
    )
    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    Integer quantidade,


    @Schema(
        description = "Preço de compra unitário do produto.",
        example = "15.90"
    )
    @NotNull(message = "O preço de compra é obrigatório.")
    @DecimalMin(
        value = "0.01",
        message = "O preço de compra deve ser maior que zero."
    )
    BigDecimal precoCompra

) {
}