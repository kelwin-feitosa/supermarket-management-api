package com.exemplo.meu_primeiro_projeto.identity.mapper;

import com.exemplo.meu_primeiro_projeto.identity.dto.request.UsuarioRequest;
import com.exemplo.meu_primeiro_projeto.identity.dto.response.UsuarioResponse;
import com.exemplo.meu_primeiro_projeto.identity.model.Usuario;

import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone()
        );
    }

    public Usuario toEntity(UsuarioRequest request) {
        return new Usuario(
                request.nome(),
                request.email(),
                request.telefone()
        );
    }
}