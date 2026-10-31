package com.exemplo.meu_primeiro_projeto.sales.mapper;

import com.exemplo.meu_primeiro_projeto.catalog.model.Produto;

import com.exemplo.meu_primeiro_projeto.sales.dto.request.ItemCarrinhoRequest;
import com.exemplo.meu_primeiro_projeto.sales.dto.response.CarrinhoResponse;
import com.exemplo.meu_primeiro_projeto.sales.dto.response.ItemCarrinhoResponse;
import com.exemplo.meu_primeiro_projeto.sales.model.Carrinho;
import com.exemplo.meu_primeiro_projeto.sales.model.ItemCarrinho;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CarrinhoMapper {

    public ItemCarrinhoResponse toItemResponse(ItemCarrinho item) {
        return new ItemCarrinhoResponse(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal()
        );
    }

    public ItemCarrinho toItemEntity(ItemCarrinhoRequest request, Produto produto) {
        return new ItemCarrinho(
                produto,
                request.quantidade(),
                produto.getPreco()
        );
    }

    public CarrinhoResponse toResponse(Carrinho carrinho, BigDecimal valorTotal) {
        List<ItemCarrinhoResponse> itens = carrinho.getItens()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return new CarrinhoResponse(
                carrinho.getId(),
                valorTotal,
                itens
        );
    }
}