package com.exemplo.meu_primeiro_projeto.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoriaResponse(

    @Schema(description = "Identificador da categoria", example = "1")
    Long id,

    @Schema(description = "Nome da categoria", example = "Bebidas")
    String nome,

    @Schema(description = "Descrição da categoria", example = "Produtos como refrigerantes e sucos")
    String descricao

) {
}