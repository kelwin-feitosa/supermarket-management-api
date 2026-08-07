package com.exemplo.meu_primeiro_projeto.dto.filter;

import java.time.LocalDate;

public record CompraFiltro(
    Long fornecedorId,
    LocalDate dataInicio,
    LocalDate dataFim
) {}
