package com.exemplo.meu_primeiro_projeto.catalog.mapper;

import com.exemplo.meu_primeiro_projeto.catalog.dto.request.ProdutoRequest;
import com.exemplo.meu_primeiro_projeto.catalog.dto.response.ProdutoResponse;
import com.exemplo.meu_primeiro_projeto.catalog.model.Categoria;
import com.exemplo.meu_primeiro_projeto.catalog.model.Produto;

import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    public ProdutoResponse toResponse(Produto produto){
        return new ProdutoResponse (
            produto.getId(),
            produto.getNome(),
            produto.getPreco(),
            produto.getDescricao(),
            produto.getQuantidadeEstoque(),
            produto.getCategoria().getId(),
            produto.getDataCadastro()
        );
    }
    
    public Produto toEntity(ProdutoRequest request, Categoria categoria) {
        return new Produto(
            request.nome(),
            request.preco(),
            request.descricao(),
            request.quantidadeEstoque(),
            categoria
        );
    }
}
