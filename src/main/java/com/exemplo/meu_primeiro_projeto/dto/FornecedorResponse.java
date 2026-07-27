package com.exemplo.meu_primeiro_projeto.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record FornecedorResponse(

    @Schema(description = "Identificador do fornecedor", example = "1")
    Long id,

    @Schema(description = "Nome do fornecedor", example = "Distribuidora ABC")
    String nome,

    @Schema(description = "CNPJ do fornecedor", example = "12345678000199")
    String cnpj,

    @Schema(description = "Telefone do fornecedor", example = "61988888888")
    String telefone

) {
}