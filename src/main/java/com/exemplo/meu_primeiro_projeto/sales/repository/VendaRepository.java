package com.exemplo.meu_primeiro_projeto.sales.repository;

import com.exemplo.meu_primeiro_projeto.sales.model.Venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VendaRepository extends JpaRepository<Venda, Long>, JpaSpecificationExecutor<Venda>{

}