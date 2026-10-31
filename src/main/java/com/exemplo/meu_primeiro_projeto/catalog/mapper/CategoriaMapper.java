package com.exemplo.meu_primeiro_projeto.catalog.mapper;

import com.exemplo.meu_primeiro_projeto.catalog.dto.request.CategoriaRequest;
import com.exemplo.meu_primeiro_projeto.catalog.dto.response.CategoriaResponse;
import com.exemplo.meu_primeiro_projeto.catalog.model.Categoria;

import org.springframework.stereotype.Component;

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
