package com.exemplo.meu_primeiro_projeto.dto.filter;

import java.math.BigDecimal;

public record ProdutoFiltro(
    String nome,
    Long categoriaId,
    BigDecimal precoMin,
    BigDecimal precoMax,
    Integer estoqueMin,
    Integer estoqueMax
) {}