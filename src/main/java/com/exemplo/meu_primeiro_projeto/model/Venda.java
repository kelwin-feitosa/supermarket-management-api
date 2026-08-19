package com.exemplo.meu_primeiro_projeto.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.exemplo.meu_primeiro_projeto.util.DataHoraUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Venda { //A venda feita pro cliente

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(
    mappedBy = "venda",
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    private List<ItemVenda> itens = new ArrayList<>();

    private LocalDateTime dataVenda;
    private BigDecimal valorTotal;

    protected Venda() {}

    public Venda(Cliente cliente) {
        this.cliente = cliente;
    }

    @PrePersist
    public void prePersist() {
        dataVenda = DataHoraUtil.agora();
    }

    public void adicionarItem(ItemVenda itemVenda) {
        itemVenda.setVenda(this);
        this.itens.add(itemVenda);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }

    public LocalDateTime getDataVenda() { return dataVenda; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

}