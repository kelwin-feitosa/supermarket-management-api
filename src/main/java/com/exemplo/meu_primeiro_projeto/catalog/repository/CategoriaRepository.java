package com.exemplo.meu_primeiro_projeto.catalog.repository;

import com.exemplo.meu_primeiro_projeto.catalog.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {
    boolean existsByNome(String nome);
}