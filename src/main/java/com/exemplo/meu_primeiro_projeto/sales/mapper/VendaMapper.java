package com.exemplo.meu_primeiro_projeto.sales.mapper;

import com.exemplo.meu_primeiro_projeto.sales.dto.response.ItemVendaResponse;
import com.exemplo.meu_primeiro_projeto.sales.dto.response.VendaResponse;
import com.exemplo.meu_primeiro_projeto.sales.model.ItemVenda;
import com.exemplo.meu_primeiro_projeto.sales.model.Venda;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class VendaMapper {

    public VendaResponse toResponse(Venda venda) {
        List<ItemVendaResponse> itens = venda.getItens()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return new VendaResponse(
                venda.getId(),
                venda.getUsuario().getId(),
                venda.getDataVenda(),
                venda.getValorTotal(),
                itens
        );
    }

    public ItemVendaResponse toItemResponse(ItemVenda item) {
        return new ItemVendaResponse(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal()
        );
    }
}