package com.exemplo.meu_primeiro_projeto.mapper;

import org.springframework.stereotype.Component;

import com.exemplo.meu_primeiro_projeto.dto.request.ProdutoRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.ProdutoResponse;
import com.exemplo.meu_primeiro_projeto.model.Categoria;
import com.exemplo.meu_primeiro_projeto.model.Produto;

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
