package com.exemplo.meu_primeiro_projeto.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados necessários para cadastrar ou atualizar um produto.")
public record ProdutoRequest(

    @Schema(
        description = "Nome do produto.",
        example = "Arroz 5kg"
    )
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    String nome,


    @Schema(
        description = "Preço de venda do produto.",
        example = "25.90"
    )
    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    BigDecimal preco,


    @Schema(
        description = "Descrição detalhada do produto.",
        example = "Arroz branco tipo 1"
    )
    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    String descricao,


    @Schema(
        description = "Quantidade disponível no estoque.",
        example = "100"
    )
    @NotNull(message = "A quantidade em estoque é obrigatória.")
    @PositiveOrZero(message = "O estoque não pode ser negativo.")
    Integer quantidadeEstoque,


    @Schema(
        description = "Identificador da categoria do produto.",
        example = "1"
    )
    @NotNull(message = "A categoria é obrigatória.")
    Long categoriaId

) { 
}