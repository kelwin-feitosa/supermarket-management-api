package com.exemplo.meu_primeiro_projeto.mapper;

import org.springframework.stereotype.Component;

import com.exemplo.meu_primeiro_projeto.dto.request.CategoriaRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CategoriaResponse;
import com.exemplo.meu_primeiro_projeto.model.Categoria;

@Component
public class CategoriaMapper {
    public CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }

    public Categoria toEntity(CategoriaRequest request) {
        return new Categoria(
                request.nome(),
                request.descricao()
        );
    }
}
