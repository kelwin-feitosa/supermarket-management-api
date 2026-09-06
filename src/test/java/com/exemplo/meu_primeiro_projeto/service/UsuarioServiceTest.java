package com.exemplo.meu_primeiro_projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

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

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @Mock
    private UsuarioFiltro filtro;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;


    @Test
    void criarUsuario_deveCriarComSucesso() {
        UsuarioRequest request = criarRequestPadrao();
        Usuario usuario = criarUsuarioPadrao();
        UsuarioResponse response = criarResponsePadrao();

        when(repository.existsByEmail(request.email()))
            .thenReturn(false);

        when(mapper.toEntity(request))
            .thenReturn(usuario);

        when(passwordEncoder.encode(request.senha()))
            .thenReturn("senha-hash");

        when(repository.save(any(Usuario.class)))
            .thenReturn(usuario);

        when(carrinhoRepository.save(any(Carrinho.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        when(mapper.toResponse(usuario))
            .thenReturn(response);

        UsuarioResponse resposta = service.criarUsuario(request);

        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.email(), resposta.email());
        assertEquals(request.telefone(), resposta.telefone());

        assertEquals("senha-hash", usuario.getSenha());
        assertEquals(usuario, usuario.getCarrinho().getUsuario());

        verify(repository).existsByEmail(request.email());
        verify(mapper).toEntity(request);
        verify(passwordEncoder).encode(request.senha());
        verify(repository).save(any(Usuario.class));
        verify(carrinhoRepository).save(any(Carrinho.class));
        verify(mapper).toResponse(usuario);
    }

    @Test
    void criarUsuario_deveLancarExcecaoQuandoEmailJaExiste() {
        UsuarioRequest request = criarRequestPadrao();

        when(repository.existsByEmail(request.email()))
            .thenReturn(true);

        assertThrows(
            UsuarioEmailJaExisteException.class,
            () -> service.criarUsuario(request)
        );

        verify(repository).existsByEmail(request.email());
        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    void listarUsuarios_deveRetornarPagina() {
        Usuario usuario1 = criarUsuarioPadrao();

        Usuario usuario2 = new Usuario(
            "Maria",
            "maria@email.com",
            "99999-9999"
        );
        usuario2.setId(2L);

        UsuarioResponse response1 = criarResponsePadrao();

        UsuarioResponse response2 = new UsuarioResponse(
            2L,
            usuario2.getNome(),
            usuario2.getEmail(),
            usuario2.getTelefone()
        );

        Pageable pageable = PageRequest.of(0, 10);

        Page<Usuario> pagina = new PageImpl<>(
            List.of(usuario1, usuario2),
            pageable,
            2
        );

        when(repository.findAll(
            ArgumentMatchers.<Specification<Usuario>>any(),
            eq(pageable)
        )).thenReturn(pagina);

        when(mapper.toResponse(usuario1))
            .thenReturn(response1);

        when(mapper.toResponse(usuario2))
            .thenReturn(response2);

        Page<UsuarioResponse> resposta = service.listarUsuarios(filtro, pageable);

        assertEquals(2, resposta.getContent().size());

        assertEquals(2, resposta.getTotalElements());
        assertEquals(1, resposta.getTotalPages());

        assertEquals(usuario1.getNome(), resposta.getContent().get(0).nome());
        assertEquals(usuario1.getEmail(), resposta.getContent().get(0).email());
        assertEquals(usuario1.getTelefone(), resposta.getContent().get(0).telefone());

        assertEquals(usuario2.getNome(), resposta.getContent().get(1).nome());
        assertEquals(usuario2.getEmail(), resposta.getContent().get(1).email());
        assertEquals(usuario2.getTelefone(), resposta.getContent().get(1).telefone());

        verify(repository).findAll(
            ArgumentMatchers.<Specification<Usuario>>any(),
            eq(pageable)
        );

        verify(mapper).toResponse(usuario1);
        verify(mapper).toResponse(usuario2);
    }

    @Test
    void buscarPorId_deveRetornarUsuarioQuandoExistir() {
        Usuario usuario = criarUsuarioPadrao();
        UsuarioResponse response = criarResponsePadrao();

        when(repository.findById(usuario.getId()))
            .thenReturn(Optional.of(usuario));

        when(mapper.toResponse(usuario))
            .thenReturn(response);

        UsuarioResponse resposta = service.buscarPorId(usuario.getId());

        assertEquals(usuario.getId(), resposta.id());
        assertEquals(usuario.getNome(), resposta.nome());
        assertEquals(usuario.getEmail(), resposta.email());
        assertEquals(usuario.getTelefone(), resposta.telefone());

        verify(repository).findById(usuario.getId());
        verify(mapper).toResponse(usuario);
    }

    @Test
    void buscarPorId_deveLancarExcecaoQuandoUsuarioNaoExistir() {
        Long id = 1L;

        when(repository.findById(id))
            .thenReturn(Optional.empty());

        assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> service.buscarPorId(id)
        );

        verify(repository).findById(id);
    }

    @Test
    void atualizarUsuario_deveAtualizarComSucesso() {
        Usuario usuario = criarUsuarioPadrao();
        UsuarioRequest request = new UsuarioRequest(
            "Kelwin Atualizado",
            "kelwin.novo@email.com",
            "88888-8888",
            "123456"
        );
        UsuarioResponse response = new UsuarioResponse(
            1L,
            request.nome(),
            request.email(),
            request.telefone()
        );


        when(repository.findById(usuario.getId()))
            .thenReturn(Optional.of(usuario));

        when(repository.existsByEmail(request.email()))
            .thenReturn(false);

        when(repository.save(any(Usuario.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        when(mapper.toResponse(usuario))
            .thenReturn(response);


        UsuarioResponse resposta = service.atualizarUsuario(usuario.getId(), request);

        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.email(), resposta.email());
        assertEquals(request.telefone(), resposta.telefone());

        verify(repository).findById(usuario.getId());
        verify(repository).existsByEmail(request.email());
        verify(repository).save(any(Usuario.class));
        verify(mapper).toResponse(usuario);
    }

    @Test
    void atualizarUsuario_deveLancarExcecaoQuandoUsuarioNaoExistir() {
        UsuarioRequest request = criarRequestPadrao();
        Long id = 1L;

        when(repository.findById(id))
            .thenReturn(Optional.empty());


        assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> service.atualizarUsuario(id, request)
        );


        verify(repository).findById(id);
        verify(repository, never()).existsByEmail(request.email());
        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    void atualizarUsuario_deveLancarExcecaoQuandoNovoEmailJaExiste() {
        Usuario usuario = criarUsuarioPadrao();

        UsuarioRequest request = new UsuarioRequest(
            "João",
            "outro@email.com",
            "77777-7777",
            "123456"
        );


        when(repository.findById(usuario.getId()))
            .thenReturn(Optional.of(usuario));


        when(repository.existsByEmail(request.email()))
            .thenReturn(true);


        assertThrows(
            UsuarioEmailJaExisteException.class,
            () -> service.atualizarUsuario(usuario.getId(), request)
        );


        verify(repository).findById(usuario.getId());
        verify(repository).existsByEmail(request.email());
        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    void deletarUsuario_deveExcluirComSucesso() {
        Usuario usuario = criarUsuarioPadrao();

        when(repository.findById(usuario.getId()))
            .thenReturn(Optional.of(usuario));


        service.deletarUsuario(usuario.getId());


        verify(repository).findById(usuario.getId());
        verify(repository).delete(usuario);
    }

    @Test
    void deletarUsuario_deveLancarExcecaoQuandoUsuarioNaoExistir() {
        Long id = 1L;

        when(repository.findById(id))
            .thenReturn(Optional.empty());


        assertThrows(
            UsuarioNaoEncontradoException.class,
            () -> service.deletarUsuario(id)
        );


        verify(repository).findById(id);
        verify(repository, never()).delete(any(Usuario.class));
    }

    private Usuario criarUsuarioPadrao() {
        Usuario usuario = new Usuario(
            "Kelwin",
            "kelwin@email.com",
            "99999-9999"
        );

        usuario.setId(1L);

        return usuario;
    }

    private UsuarioRequest criarRequestPadrao() {
        return new UsuarioRequest(
            "Kelwin",
            "kelwin@email.com",
            "99999-9999",
            "123456"
        );
    }

    private UsuarioResponse criarResponsePadrao() {
        Usuario usuario = criarUsuarioPadrao();

        return new UsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone()
        );
    }
}