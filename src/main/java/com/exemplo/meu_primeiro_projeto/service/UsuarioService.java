package com.exemplo.meu_primeiro_projeto.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.filter.UsuarioFiltro;
import com.exemplo.meu_primeiro_projeto.dto.request.UsuarioRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.UsuarioResponse;
import com.exemplo.meu_primeiro_projeto.exception.UsuarioEmailJaExisteException;
import com.exemplo.meu_primeiro_projeto.exception.UsuarioNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.mapper.UsuarioMapper;
import com.exemplo.meu_primeiro_projeto.model.Carrinho;
import com.exemplo.meu_primeiro_projeto.model.Usuario;
import com.exemplo.meu_primeiro_projeto.repository.CarrinhoRepository;
import com.exemplo.meu_primeiro_projeto.repository.UsuarioRepository;
import com.exemplo.meu_primeiro_projeto.repository.specification.UsuarioSpecification;

import lombok.RequiredArgsConstructor;

@Service    
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final CarrinhoRepository carrinhoRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<UsuarioResponse> listarUsuarios(UsuarioFiltro filtro, Pageable pageable) {
        return repository.findAll(UsuarioSpecification.comFiltro(filtro), pageable)
                        .map(mapper::toResponse);
        }

    public UsuarioResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    public UsuarioResponse criarUsuario(UsuarioRequest request) {
        verificarDuplicidade(request);

        Usuario usuario = mapper.toEntity(request);

        usuario.setSenha(passwordEncoder.encode(request.senha()));

        Usuario usuarioSalvo = repository.save(usuario);


        Carrinho carrinho = new Carrinho(usuarioSalvo);
        usuarioSalvo.setCarrinho(carrinho);
        carrinhoRepository.save(carrinho);

        return mapper.toResponse(usuarioSalvo);
    }

    public UsuarioResponse atualizarUsuario(Long id, UsuarioRequest request) {
        Usuario usuario = buscarEntidade(id);

        verificarDuplicidade(usuario, request);

        usuario.atualizar(
            request.nome(),
            request.email(),
            request.telefone()
        );

        return mapper.toResponse(repository.save(usuario));
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = buscarEntidade(id);
        
        repository.delete(usuario);
    }

    private Usuario buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado."));
    }

    private void verificarDuplicidade(UsuarioRequest request) {
        if(repository.existsByEmail(request.email())) {
            throw new UsuarioEmailJaExisteException("Esse email já está cadastrado.");
        }
    }

    private void verificarDuplicidade(Usuario usuario, UsuarioRequest request) {
        if(!usuario.getEmail().equals(request.email())) {
            verificarDuplicidade(request);
        }
    }
}