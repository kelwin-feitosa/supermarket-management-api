package com.exemplo.meu_primeiro_projeto.service;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.exception.EstoqueInsuficienteException;
import com.exemplo.meu_primeiro_projeto.model.Produto;

@Service
public class EstoqueService {
    
    public void verificarEstoque(Produto produto, int quantidade) {
        if(quantidade > produto.getQuantidadeEstoque()) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto.");
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
