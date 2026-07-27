package com.exemplo.meu_primeiro_projeto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados necessários para cadastrar ou atualizar uma categoria.")
public record CategoriaRequest(

    @Schema(
        description = "Nome da categoria do produto.",
        example = "Bebidas"
    )
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    String nome,


    @Schema(
        description = "Descrição da categoria.",
        example = "Produtos como refrigerantes, sucos e águas."
    )
    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    String descricao

) {
}