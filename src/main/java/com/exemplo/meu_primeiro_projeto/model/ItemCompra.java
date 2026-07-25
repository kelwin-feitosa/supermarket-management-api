package com.exemplo.meu_primeiro_projeto.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemCompra { //Itens da compra com o fornecedor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Produto produto;

    @ManyToOne(optional = false)
    private Compra compra;

    private Integer quantidade;
    private BigDecimal precoCompra;
    private BigDecimal subtotal;

    protected ItemCompra() {}

    public ItemCompra(Produto produto, Integer quantidade, BigDecimal precoCompra) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoCompra = precoCompra;
        atualizarSubtotal();
    }

    private void atualizarSubtotal() {
        this.subtotal = precoCompra.multiply(BigDecimal.valueOf(quantidade));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) { this.compra = compra; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { 
        this.quantidade = quantidade; 
        atualizarSubtotal();
    }

    public BigDecimal getPrecoCompra() { return precoCompra; }
    public void setPrecoCompra(BigDecimal precoCompra) { 
        this.precoCompra = precoCompra; 
        atualizarSubtotal();
    }

    public BigDecimal getSubtotal() { return this.subtotal; }

}