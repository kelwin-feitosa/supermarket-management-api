package com.exemplo.meu_primeiro_projeto.procurement.dto.filter;


public record FornecedorFiltro(
    String nome,
    String cnpj,
    Boolean ativo
) {}
