package com.exemplo.meu_primeiro_projeto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProdutoResponse(

    @Schema(description = "Identificador único do produto", example = "1")
    Long id,

    @Schema(description = "Nome do produto", example = "Arroz 5kg")
    String nome,

    @Schema(description = "Preço de venda do produto", example = "25.90")
    BigDecimal preco,

    @Schema(description = "Descrição do produto", example = "Arroz branco tipo 1")
    String descricao,

    @Schema(description = "Quantidade disponível em estoque", example = "100")
    Integer quantidadeEstoque,

    @Schema(description = "Identificador da categoria relacionada ao produto", example = "2")
    Long categoriaId,

    @Schema(description = "Data e hora de cadastro do produto")
    LocalDateTime dataCadastro

) {
}