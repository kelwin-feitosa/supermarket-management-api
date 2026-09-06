package com.exemplo.meu_primeiro_projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exemplo.meu_primeiro_projeto.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario>{
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}