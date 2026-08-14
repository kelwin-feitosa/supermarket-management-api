package com.exemplo.meu_primeiro_projeto.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.exemplo.meu_primeiro_projeto.dto.filter.CategoriaFiltro;
import com.exemplo.meu_primeiro_projeto.model.Categoria;

public class CategoriaSpecification {

    public static Specification<Categoria> nomeContem(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
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