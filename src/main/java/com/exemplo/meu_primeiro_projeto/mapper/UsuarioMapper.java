package com.exemplo.meu_primeiro_projeto.mapper;

import org.springframework.stereotype.Component;

import com.exemplo.meu_primeiro_projeto.dto.request.UsuarioRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.UsuarioResponse;
import com.exemplo.meu_primeiro_projeto.model.Usuario;

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