package com.exemplo.meu_primeiro_projeto.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.exemplo.meu_primeiro_projeto.dto.request.ItemCompraRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CompraResponse;
import com.exemplo.meu_primeiro_projeto.dto.response.ItemCompraResponse;
import com.exemplo.meu_primeiro_projeto.model.Compra;
import com.exemplo.meu_primeiro_projeto.model.ItemCompra;
import com.exemplo.meu_primeiro_projeto.model.Produto;

@Component
public class CompraMapper {

    public CompraResponse toResponse(Compra compra) {
        List<ItemCompraResponse> itens = compra.getItens()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return new CompraResponse(
                compra.getId(),
                compra.getFornecedor().getId(),
                compra.getDataCompra(),
                compra.getValorTotal(),
                itens
        );
    }

    public ItemCompraResponse toItemResponse(ItemCompra item) {
        return new ItemCompraResponse(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoCompra(),
                item.getSubtotal()
        );
    }

    public ItemCompra toItemEntity(ItemCompraRequest request, Produto produto) {
        return new ItemCompra(
                produto,
                request.quantidade(),
                request.precoCompra()
        );
    }
}