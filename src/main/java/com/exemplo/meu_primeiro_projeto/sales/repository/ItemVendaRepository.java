package com.exemplo.meu_primeiro_projeto.sales.repository;

import com.exemplo.meu_primeiro_projeto.sales.model.ItemVenda;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

}