package com.exemplo.meu_primeiro_projeto.procurement.repository;

import com.exemplo.meu_primeiro_projeto.procurement.model.ItemCompra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {

}