package com.exemplo.meu_primeiro_projeto.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.model.ItemCarrinho;
import com.exemplo.meu_primeiro_projeto.model.ItemCompra;
import com.exemplo.meu_primeiro_projeto.model.ItemVenda;

@Service
public class CalculoPrecoService {

    
    public BigDecimal calcularValorTotal(List<ItemCarrinho> itens) {
        return itens.stream()
            .map(ItemCarrinho::getSubtotal)
            .reduce(BigDecimal.ZERO, (total, subtotal) -> total.add(subtotal));
                                 //, BigDecimal::add));
    }

    public BigDecimal calcularValorTotalVenda(List<ItemVenda> itens) {
        return itens.stream()
                .map(item -> item.getSubtotal())
                   //ItemVenda::Subtotal
                .reduce(BigDecimal.ZERO, (total, subtotal) -> total.add(subtotal));
    }                                //, BigDecimal::add));

    public BigDecimal calcularValorTotalCompra(List<ItemCompra> itens) {
        return itens.stream()
                .map(ItemCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
