package com.exemplo.meu_primeiro_projeto.identity.repository.specification;

import com.exemplo.meu_primeiro_projeto.identity.dto.filter.UsuarioFiltro;
import com.exemplo.meu_primeiro_projeto.identity.model.Usuario;

import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {

    private UsuarioSpecification() {}

    public static Specification<Usuario> nomeContem(String nome) {
        if (nome == null || nome.isBlank()) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("nome")),
                "%" + nome.toLowerCase() + "%"
            );
    }

    public static Specification<Usuario> emailContem(String email) {
        if (email == null || email.isBlank()) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("email")),
                "%" + email.toLowerCase() + "%"
            );
    }

    public static Specification<Usuario> comFiltro(UsuarioFiltro filtro) {
        return Specification.allOf(
            nomeContem(filtro.nome()),
            emailContem(filtro.email())
        );
    }
}