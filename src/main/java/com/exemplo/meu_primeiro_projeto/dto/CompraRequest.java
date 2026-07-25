package com.exemplo.meu_primeiro_projeto.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CompraRequest(

    @NotNull(message = "O fornecedor é obrigatório.")
    Long fornecedorId,

    @NotEmpty(message = "A compra deve possuir ao menos um item.")
    List<@Valid ItemCompraRequest> itens
) {
}