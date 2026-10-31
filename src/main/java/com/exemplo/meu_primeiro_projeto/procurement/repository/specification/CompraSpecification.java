package com.exemplo.meu_primeiro_projeto.procurement.repository.specification;

import com.exemplo.meu_primeiro_projeto.procurement.dto.filter.CompraFiltro;
import com.exemplo.meu_primeiro_projeto.procurement.model.Compra;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

public class CompraSpecification {

    private CompraSpecification() {}

    public static Specification<Compra> fornecedorIgual(Long idFornecedor) {
        if (idFornecedor == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(
                root.get("fornecedor").get("id"),
                idFornecedor
            );
    }

    public static Specification<Compra> dataCompraMaiorOuIgual(LocalDate dataInicio) {
        if (dataInicio == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(
                root.get("dataCompra"),
                dataInicio
            );
    }

    public static Specification<Compra> dataCompraMenorOuIgual(LocalDate dataFim) {
        if (dataFim == null) {
            return Specification.unrestricted();
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