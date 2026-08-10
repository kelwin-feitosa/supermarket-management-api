package com.exemplo.meu_primeiro_projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exemplo.meu_primeiro_projeto.dto.request.ClienteRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.ClienteResponse;
import com.exemplo.meu_primeiro_projeto.exception.ClienteEmailJaExisteException;
import com.exemplo.meu_primeiro_projeto.exception.ClienteNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.model.Cliente;
import com.exemplo.meu_primeiro_projeto.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void criarCliente_deveCriarComSucesso() {
        ClienteRequest request = criarRequestPadrao();
        Cliente cliente = criarClientePadrao();

        when(repository.existsByEmail(request.email()))
            .thenReturn(false);

        when(repository.save(any(Cliente.class)))
            .thenReturn(cliente);

        ClienteResponse resposta = service.criarCliente(request);


        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.email(), resposta.email());
        assertEquals(request.telefone(), resposta.telefone());

        verify(repository).existsByEmail(request.email());
        verify(repository).save(any(Cliente.class));
    }

    @Test
    void criarCliente_deveLancarExcecaoQuandoEmailJaExiste() {
        ClienteRequest request = criarRequestPadrao();

        when(repository.existsByEmail(request.email()))
            .thenReturn(true);

        assertThrows(
            ClienteEmailJaExisteException.class,
            () -> service.criarCliente(request)
        );

        verify(repository).existsByEmail(request.email());
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    void listarClientes_deveRetornarLista() {
        Cliente cliente1 = criarClientePadrao();
        Cliente cliente2 = new Cliente(
            "Maria",
            "maria@email.com",
            "99999-9999"
        );
        cliente2.setId(2L);


        when(repository.findAll())
            .thenReturn(List.of(cliente1, cliente2));


        List<ClienteResponse> resposta = service.listarClientes();


        assertEquals(2, resposta.size());

        assertEquals(cliente1.getNome(), resposta.get(0).nome());
        assertEquals(cliente1.getEmail(), resposta.get(0).email());
        assertEquals(cliente1.getTelefone(), resposta.get(0).telefone());

        assertEquals(cliente2.getNome(), resposta.get(1).nome());
        assertEquals(cliente2.getEmail(), resposta.get(1).email());
        assertEquals(cliente2.getTelefone(), resposta.get(1).telefone());

        verify(repository).findAll();
    }

    @Test
    void buscarPorId_deveRetornarClienteQuandoExistir() {
        Cliente cliente = criarClientePadrao();

        when(repository.findById(cliente.getId()))
            .thenReturn(Optional.of(cliente));

        ClienteResponse resposta = service.buscarPorId(cliente.getId());

        assertEquals(cliente.getId(), resposta.id());
        assertEquals(cliente.getNome(), resposta.nome());
        assertEquals(cliente.getEmail(), resposta.email());
        assertEquals(cliente.getTelefone(), resposta.telefone());

        verify(repository).findById(cliente.getId());
    }

    @Test
    void buscarPorId_deveLancarExcecaoQuandoClienteNaoExistir() {
        Long id = 1L;

        when(repository.findById(id))
            .thenReturn(Optional.empty());

        assertThrows(
            ClienteNaoEncontradoException.class,
            () -> service.buscarPorId(id)
        );

        verify(repository).findById(id);
    }

    @Test
    void atualizarCliente_deveAtualizarComSucesso() {
        Cliente cliente = criarClientePadrao();
        ClienteRequest request = new ClienteRequest(
            "Kelwin Atualizado",
            "kelwin.novo@email.com",
            "88888-8888"
        );


        when(repository.findById(cliente.getId()))
            .thenReturn(Optional.of(cliente));

        when(repository.existsByEmail(request.email()))
            .thenReturn(false);

        when(repository.save(any(Cliente.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));


        ClienteResponse resposta = service.atualizarCliente(cliente.getId(), request);

        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.email(), resposta.email());
        assertEquals(request.telefone(), resposta.telefone());

        verify(repository).findById(cliente.getId());
        verify(repository).existsByEmail(request.email());
        verify(repository).save(any(Cliente.class));
    }

    @Test
    void atualizarCliente_deveLancarExcecaoQuandoClienteNaoExistir() {
        ClienteRequest request = criarRequestPadrao();
        Long id = 1L;

        when(repository.findById(id))
            .thenReturn(Optional.empty());


        assertThrows(
            ClienteNaoEncontradoException.class,
            () -> service.atualizarCliente(id, request)
        );


        verify(repository).findById(id);
        verify(repository, never()).existsByEmail(request.email());
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    void atualizarCliente_deveLancarExcecaoQuandoNovoEmailJaExiste() {
        Cliente cliente = criarClientePadrao();

        ClienteRequest request = new ClienteRequest(
            "João",
            "outro@email.com",
            "77777-7777"
        );


        when(repository.findById(cliente.getId()))
            .thenReturn(Optional.of(cliente));


        when(repository.existsByEmail(request.email()))
            .thenReturn(true);


        assertThrows(
            ClienteEmailJaExisteException.class,
            () -> service.atualizarCliente(cliente.getId(), request)
        );


        verify(repository).findById(cliente.getId());
        verify(repository).existsByEmail(request.email());
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    void deletarCliente_deveExcluirComSucesso() {
        Cliente cliente = criarClientePadrao();

        when(repository.findById(cliente.getId()))
            .thenReturn(Optional.of(cliente));


        service.deletarCliente(cliente.getId());


        verify(repository).findById(cliente.getId());
        verify(repository).delete(cliente);
    }

    @Test
    void deletarCliente_deveLancarExcecaoQuandoClienteNaoExistir() {
        Long id = 1L;

        when(repository.findById(id))
            .thenReturn(Optional.empty());


        assertThrows(
            ClienteNaoEncontradoException.class,
            () -> service.deletarCliente(id)
        );


        verify(repository).findById(id);
        verify(repository, never()).delete(any(Cliente.class));
    }

    private Cliente criarClientePadrao() {
        Cliente cliente = new Cliente(
            "Kelwin",
            "kelwin@email.com",
            "99999-9999"
        );

        cliente.setId(1L);

        return cliente;
    }

    private ClienteRequest criarRequestPadrao() {
        return new ClienteRequest(
            "Kelwin",
            "kelwin@email.com",
            "99999-9999"
        );
    }
}