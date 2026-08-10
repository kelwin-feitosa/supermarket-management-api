package com.exemplo.meu_primeiro_projeto.repository.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.exemplo.meu_primeiro_projeto.dto.filter.ProdutoFiltro;
import com.exemplo.meu_primeiro_projeto.model.Produto;

public class ProdutoSpecification {

    public static Specification<Produto> nomeContem(String nome) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(
                root.get("nome"),
                "%" + nome + "%"
            );
    }

    public static Specification<Produto> categoriaIgual(Long categoriaId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(
                root.get("categoria").get("id"),
                categoriaId
            );
    }       

    public static Specification<Produto> precoMaiorOuIgual(BigDecimal precoMin) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(
                root.get("preco"),
                precoMin
            );
    }

    public static Specification<Produto> precoMenorOuIgual(BigDecimal precoMax) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo(
                root.get("preco"),
                precoMax
            );
    }

    public static Specification<Produto> estoqueMaiorOuIgual(Integer estoqueMin) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(
                root.get("quantidadeEstoque"),
                estoqueMin
            );
    }

    public static Specification<Produto> estoqueMenorOuIgual(Integer estoqueMax) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo(
                root.get("quantidadeEstoque"),
                estoqueMax
            );
    }

    public static Specification<Produto> comFiltro(ProdutoFiltro filtro) {
        Specification<Produto> specification = (root, query, criteriaBuilder) -> null;

        if(filtro.nome() != null && !filtro.nome().isBlank()) {
            specification = specification.and(
                nomeContem(filtro.nome())
            );
        }

        if(filtro.categoriaId() != null) {
            specification = specification.and(
                categoriaIgual(filtro.categoriaId())
            );
        }

        if (filtro.precoMin() != null) {
            specification = specification.and(
                precoMaiorOuIgual(filtro.precoMin())
            );
        }

        if (filtro.precoMax() != null) {
            specification = specification.and(
                precoMenorOuIgual(filtro.precoMax())
            );
        }

        if (filtro.estoqueMin() != null) {
            specification = specification.and(
                estoqueMaiorOuIgual(filtro.estoqueMin())
            );
        }

        if (filtro.estoqueMax() != null) {
            specification = specification.and(
                estoqueMenorOuIgual(filtro.estoqueMax())
            );
        }

        return specification;
    }
}