package com.exemplo.meu_primeiro_projeto.procurement.mapper;

import com.exemplo.meu_primeiro_projeto.catalog.model.Produto;

import com.exemplo.meu_primeiro_projeto.procurement.dto.request.ItemCompraRequest;
import com.exemplo.meu_primeiro_projeto.procurement.dto.response.CompraResponse;
import com.exemplo.meu_primeiro_projeto.procurement.dto.response.ItemCompraResponse;
import com.exemplo.meu_primeiro_projeto.procurement.model.Compra;
import com.exemplo.meu_primeiro_projeto.procurement.model.ItemCompra;
import java.util.List;

import org.springframework.stereotype.Component;

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