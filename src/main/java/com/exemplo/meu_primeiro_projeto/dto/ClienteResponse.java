package com.exemplo.meu_primeiro_projeto.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClienteResponse(

    @Schema(description = "Identificador do cliente", example = "1")
    Long id,

    @Schema(description = "Nome completo do cliente", example = "João Silva")
    String nome,

    @Schema(description = "E-mail do cliente", example = "joao@email.com")
    String email,

    @Schema(description = "Telefone do cliente", example = "61999999999")
    String telefone

) {
}