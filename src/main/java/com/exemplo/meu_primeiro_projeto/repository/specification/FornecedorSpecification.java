package com.exemplo.meu_primeiro_projeto.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.exemplo.meu_primeiro_projeto.dto.filter.FornecedorFiltro;
import com.exemplo.meu_primeiro_projeto.model.Fornecedor;

public class FornecedorSpecification {

    public static Specification<Fornecedor> nomeContem(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("nome")),
                "%" + nome.toLowerCase() + "%"
            );
    }

    public static Specification<Fornecedor> cnpjContem(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                root.get("cnpj"),
                "%" + cnpj + "%"
            );
    }

    public static Specification<Fornecedor> comFiltro(FornecedorFiltro filtro) {
        return Specification.allOf(
            nomeContem(filtro.nome()),
            cnpjContem(filtro.cnpj())
        );
    }
}