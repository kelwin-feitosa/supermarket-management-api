package com.exemplo.meu_primeiro_projeto.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados necessários para registrar uma compra de produtos.")
public record CompraRequest(

    @Schema(
        description = "Identificador do fornecedor responsável pela compra.",
        example = "1"
    )
    @NotNull(message = "O fornecedor é obrigatório.")
    Long fornecedorId,


    @Schema(
        description = "Lista de produtos e quantidades adquiridas na compra."
    )
    @NotEmpty(message = "A compra deve possuir ao menos um item.")
    List<@Valid ItemCompraRequest> itens

) {
}