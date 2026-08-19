package com.exemplo.meu_primeiro_projeto.repository.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.exemplo.meu_primeiro_projeto.dto.filter.VendaFiltro;
import com.exemplo.meu_primeiro_projeto.model.Venda;

public class VendaSpecification {

    private VendaSpecification() {}

    public static Specification<Venda> clienteIgual(Long clienteId) {
        if (clienteId == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(
                root.get("cliente").get("id"),
                clienteId
            );
    }

    public static Specification<Venda> dataVendaMaiorOuIgual(LocalDate dataInicio) {
        if (dataInicio == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(
                root.get("dataVenda"),
                dataInicio
            );
    }

    public static Specification<Venda> dataVendaMenorOuIgual(LocalDate dataFim) {
        if (dataFim == null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo(
                root.get("dataVenda"),
                dataFim
            );
    }

    public static Specification<Venda> comFiltro(VendaFiltro filtro) {
        return Specification.allOf(
            clienteIgual(filtro.clienteId()),
            dataVendaMaiorOuIgual(filtro.dataInicio()),
            dataVendaMenorOuIgual(filtro.dataFim())
        );
    }
}