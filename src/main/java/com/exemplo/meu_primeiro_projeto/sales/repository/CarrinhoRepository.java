package com.exemplo.meu_primeiro_projeto.sales.repository;

import com.exemplo.meu_primeiro_projeto.sales.model.Carrinho;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

}
