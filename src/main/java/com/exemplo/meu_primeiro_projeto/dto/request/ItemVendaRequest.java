package com.exemplo.meu_primeiro_projeto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados necessários para adicionar um item em uma venda.")
public record ItemVendaRequest(

    @Schema(
        description = "Identificador do produto vendido.",
        example = "1"
    )
    @NotNull(message = "O produto é obrigatório.")
    Long produtoId,


    @Schema(
        description = "Quantidade do produto vendido.",
        example = "3"
    )
    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    Integer quantidade

) {
}