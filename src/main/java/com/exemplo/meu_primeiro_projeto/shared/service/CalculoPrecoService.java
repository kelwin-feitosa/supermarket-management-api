package com.exemplo.meu_primeiro_projeto.shared.service;

import com.exemplo.meu_primeiro_projeto.procurement.model.ItemCompra;
import com.exemplo.meu_primeiro_projeto.sales.model.ItemCarrinho;
import com.exemplo.meu_primeiro_projeto.sales.model.ItemVenda;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalculoPrecoService {

    
    public BigDecimal calcularValorTotal(List<ItemCarrinho> itens) {
        return itens.stream()
            .map(ItemCarrinho::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularValorTotalVenda(List<ItemVenda> itens) {
        return itens.stream()
                .map(ItemVenda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }                                

    public BigDecimal calcularValorTotalCompra(List<ItemCompra> itens) {
        return itens.stream()
                .map(ItemCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
