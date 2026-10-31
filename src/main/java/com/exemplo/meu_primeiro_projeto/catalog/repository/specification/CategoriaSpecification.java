package com.exemplo.meu_primeiro_projeto.catalog.repository.specification;

import com.exemplo.meu_primeiro_projeto.catalog.dto.filter.CategoriaFiltro;
import com.exemplo.meu_primeiro_projeto.catalog.model.Categoria;

import org.springframework.data.jpa.domain.Specification;

public class CategoriaSpecification {

    private CategoriaSpecification() {}

    public static Specification<Categoria> nomeContem(String nome) {
        if (nome == null || nome.isBlank()) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("nome")),
                "%" + nome.toLowerCase() + "%"
            );
    }

    public static Specification<Categoria> comFiltro(CategoriaFiltro filtro) {
        return Specification.allOf(
            nomeContem(filtro.nome())
        );
    }
}