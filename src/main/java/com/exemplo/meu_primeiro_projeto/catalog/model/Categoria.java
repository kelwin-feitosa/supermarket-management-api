package com.exemplo.meu_primeiro_projeto.catalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos = new ArrayList<>();
    
    private String nome;
    private String descricao;

    protected Categoria() {}

    public Categoria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    //Request para Categoria
    public void atualizar(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<Produto> getProdutos() { return produtos; }
    public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

}