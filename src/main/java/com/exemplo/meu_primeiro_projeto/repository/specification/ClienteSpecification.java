package com.exemplo.meu_primeiro_projeto.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.exemplo.meu_primeiro_projeto.dto.filter.ClienteFiltro;
import com.exemplo.meu_primeiro_projeto.model.Cliente;

public class ClienteSpecification {

    public static Specification<Cliente> nomeContem(String nome) {
        if (nome == null || nome.isBlank()) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("nome")),
                "%" + nome.toLowerCase() + "%"
            );
    }

    public static Specification<Cliente> emailContem(String email) {
        if (email == null || email.isBlank()) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("email")),
                "%" + email.toLowerCase() + "%"
            );
    }

    public static Specification<Cliente> comFiltro(ClienteFiltro filtro) {
        return Specification.allOf(
            nomeContem(filtro.nome()),
            emailContem(filtro.email())
        );
    }
}