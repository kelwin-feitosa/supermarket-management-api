package com.exemplo.meu_primeiro_projeto.dto.filter;

import java.time.LocalDate;

public record VendaFiltro(
    Long clienteId,
    LocalDate dataInicio,
    LocalDate dataFim
) {}