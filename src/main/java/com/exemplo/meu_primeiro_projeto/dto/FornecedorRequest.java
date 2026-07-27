package com.exemplo.meu_primeiro_projeto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados necessários para cadastrar ou atualizar um fornecedor.")
public record FornecedorRequest(

    @Schema(
        description = "Nome do fornecedor ou empresa fornecedora.",
        example = "Distribuidora São Paulo LTDA"
    )
    @NotBlank(message = "O nome é obrigatório.")
    String nome,


    @Schema(
        description = "CNPJ do fornecedor contendo 14 dígitos.",
        example = "12345678000199"
    )
    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(
        regexp = "^\\d{14}$",
        message = "O CNPJ deve conter exatamente 14 dígitos."
    )
    String cnpj,


    @Schema(
        description = "Número de telefone para contato do fornecedor.",
        example = "61999999999"
    )
    @NotBlank(message = "O telefone é obrigatório.")
    @Size(
        min = 10,
        max = 11,
        message = "Telefone inválido."
    )
    String telefone

) { 
}