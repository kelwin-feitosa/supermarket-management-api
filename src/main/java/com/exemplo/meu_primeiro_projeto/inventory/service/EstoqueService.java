package com.exemplo.meu_primeiro_projeto.inventory.service;

import com.exemplo.meu_primeiro_projeto.catalog.model.Produto;

import com.exemplo.meu_primeiro_projeto.shared.exception.BusinessRuleException;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {
    
    public void verificarEstoque(Produto produto, int quantidade) {
        if(quantidade > produto.getQuantidadeEstoque()) {
            throw new BusinessRuleException("Estoque insuficiente para o produto.");
        }
    }

    public void baixarEstoque(Produto produto, int quantidade) {
        verificarEstoque(produto, quantidade);

        produto.setQuantidadeEstoque(
            produto.getQuantidadeEstoque() - quantidade
        );
    }

    public void aumentarEstoque(Produto produto, int quantidade) {
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
    }

}
