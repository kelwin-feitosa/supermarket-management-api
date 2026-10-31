package com.exemplo.meu_primeiro_projeto.identity.security.service;

import com.exemplo.meu_primeiro_projeto.identity.model.Usuario;
import com.exemplo.meu_primeiro_projeto.identity.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service 
@RequiredArgsConstructor 
public class UsuarioDetailsService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        return User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getRole().name())
                .build();
    }
}
