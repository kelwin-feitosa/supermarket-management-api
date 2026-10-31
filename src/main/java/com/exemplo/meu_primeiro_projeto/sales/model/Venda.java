package com.exemplo.meu_primeiro_projeto.sales.model;

import com.exemplo.meu_primeiro_projeto.identity.model.Usuario;
import com.exemplo.meu_primeiro_projeto.shared.util.DataHoraUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venda { //A venda feita pro cliente

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(
    mappedBy = "venda",
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    private List<ItemVenda> itens = new ArrayList<>();

    private OffsetDateTime dataVenda;
    private BigDecimal valorTotal;

    protected Venda() {}

    public Venda(Usuario usuario) {
        this.usuario = usuario;
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

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }

    public OffsetDateTime getDataVenda() { return dataVenda; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

}