package com.exemplo.meu_primeiro_projeto.repository.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.exemplo.meu_primeiro_projeto.dto.filter.CompraFiltro;
import com.exemplo.meu_primeiro_projeto.model.Compra;

public class CompraSpecification {

    public static Specification<Compra> fornecedorIgual(Long idFornecedor) {
        if (idFornecedor == null) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(
                root.get("fornecedor").get("id"),
                idFornecedor
            );
    }

    public static Specification<Compra> dataCompraMaiorOuIgual(LocalDate dataInicio) {
        if (dataInicio == null) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(
                root.get("dataCompra"),
                dataInicio
            );
    }

    public static Specification<Compra> dataCompraMenorOuIgual(LocalDate dataFim) {
        if (dataFim == null) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo(
                root.get("dataCompra"),
                dataFim
            );
    }

    public static Specification<Compra> comFiltro(CompraFiltro filtro) {
        return Specification.allOf(
            fornecedorIgual(filtro.fornecedorId()),
            dataCompraMaiorOuIgual(filtro.dataInicio()),
            dataCompraMenorOuIgual(filtro.dataFim())
        );
    }
}