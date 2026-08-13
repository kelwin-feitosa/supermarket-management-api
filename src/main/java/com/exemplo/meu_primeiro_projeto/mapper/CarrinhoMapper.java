package com.exemplo.meu_primeiro_projeto.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.exemplo.meu_primeiro_projeto.dto.request.ItemCarrinhoRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CarrinhoResponse;
import com.exemplo.meu_primeiro_projeto.dto.response.ItemCarrinhoResponse;
import com.exemplo.meu_primeiro_projeto.model.Carrinho;
import com.exemplo.meu_primeiro_projeto.model.ItemCarrinho;
import com.exemplo.meu_primeiro_projeto.model.Produto;

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