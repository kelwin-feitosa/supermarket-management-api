package com.exemplo.meu_primeiro_projeto.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "cliente_id", unique = true)
    private Cliente cliente;

    @OneToMany(
    mappedBy = "carrinho",
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    private List<ItemCarrinho> itens = new ArrayList<>(); //Inicializar Vazia

    protected Carrinho() { }

    public Carrinho(Cliente cliente) {
        this.cliente = cliente;
    }

    public void adicionarItem(ItemCarrinho item) {
        item.setCarrinho(this);
        //como estamos na mesma classe, então o getItens não é necessário
        this.itens.add(item);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<ItemCarrinho> getItens() { return itens; }
    public void setItens(List<ItemCarrinho> itens) { this.itens = itens; }

}