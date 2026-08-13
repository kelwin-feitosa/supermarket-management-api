package com.exemplo.meu_primeiro_projeto.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.exemplo.meu_primeiro_projeto.dto.response.ItemVendaResponse;
import com.exemplo.meu_primeiro_projeto.dto.response.VendaResponse;
import com.exemplo.meu_primeiro_projeto.model.ItemVenda;
import com.exemplo.meu_primeiro_projeto.model.Venda;

@Component
public class VendaMapper {

    public VendaResponse toResponse(Venda venda) {
        List<ItemVendaResponse> itens = venda.getItens()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return new VendaResponse(
                venda.getId(),
                venda.getCliente().getId(),
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