package com.exemplo.meu_primeiro_projeto.identity.model;

import com.exemplo.meu_primeiro_projeto.identity.enums.Role;

import com.exemplo.meu_primeiro_projeto.sales.model.Carrinho;
import com.exemplo.meu_primeiro_projeto.sales.model.Venda;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "usuario")
    private Carrinho carrinho;

    @OneToMany(mappedBy = "usuario")
    private List<Venda> vendas = new ArrayList<>();
    
    private String nome;
    private String email;
    private String telefone;
    private String senha;


    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;

    protected Usuario() {}

    public Usuario(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public void atualizar(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Carrinho getCarrinho() { return carrinho; }
    public void setCarrinho(Carrinho carrinho) { this.carrinho = carrinho; }

    public List<Venda> getVendas() { return vendas; }
    public void setVendas(List<Venda> vendas) { this.vendas = vendas; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Role getRole() { return role; }
}