package com.exemplo.meu_primeiro_projeto.repository.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.exemplo.meu_primeiro_projeto.dto.filter.ProdutoFiltro;
import com.exemplo.meu_primeiro_projeto.model.Produto;

public class ProdutoSpecification {

    private ProdutoSpecification() {}

    public static Specification<Produto> nomeContem(String nome) {
        if (nome == null || nome.isBlank()) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("nome")),
                "%" + nome.toLowerCase() + "%"
            );
    }

    public static Specification<Produto> categoriaIgual(Long categoriaId) {
        if (categoriaId == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(
                root.get("categoria").get("id"),
                categoriaId
            );
    }

    public static Specification<Produto> precoMaiorOuIgual(BigDecimal precoMin) {
        if (precoMin == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(
                root.get("preco"),
                precoMin
            );
    }

    public static Specification<Produto> precoMenorOuIgual(BigDecimal precoMax) {
        if (precoMax == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo(
                root.get("preco"),
                precoMax
            );
    }

    public static Specification<Produto> estoqueMaiorOuIgual(Integer estoqueMin) {
        if (estoqueMin == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(
                root.get("quantidadeEstoque"),
                estoqueMin
            );
    }

    public static Specification<Produto> estoqueMenorOuIgual(Integer estoqueMax) {
        if (estoqueMax == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo(
                root.get("quantidadeEstoque"),
                estoqueMax
            );
    }

    public static Specification<Produto> comFiltro(ProdutoFiltro filtro) {
        return Specification.allOf(
            nomeContem(filtro.nome()),
            categoriaIgual(filtro.categoriaId()),
            precoMaiorOuIgual(filtro.precoMin()),
            precoMenorOuIgual(filtro.precoMax()),
            estoqueMaiorOuIgual(filtro.estoqueMin()),
            estoqueMenorOuIgual(filtro.estoqueMax())
        );
    }
}