package com.exemplo.meu_primeiro_projeto.procurement.repository;

import com.exemplo.meu_primeiro_projeto.procurement.model.Compra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompraRepository extends JpaRepository<Compra, Long>, JpaSpecificationExecutor<Compra> {

}