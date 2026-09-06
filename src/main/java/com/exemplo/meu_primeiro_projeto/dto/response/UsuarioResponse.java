package com.exemplo.meu_primeiro_projeto.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioResponse(

    @Schema(description = "Identificador do usuário", example = "1")
    Long id,

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    String nome,

    @Schema(description = "E-mail do usuário", example = "joao@email.com")
    String email,

    @Schema(description = "Telefone do usuário", example = "61999999999")
    String telefone

) {
}