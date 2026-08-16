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

import com.exemplo.meu_primeiro_projeto.dto.request.FornecedorRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.FornecedorResponse;
import com.exemplo.meu_primeiro_projeto.exception.CnpjJaCadastradoException;
import com.exemplo.meu_primeiro_projeto.exception.FornecedorNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.mapper.FornecedorMapper;
import com.exemplo.meu_primeiro_projeto.model.Compra;
import com.exemplo.meu_primeiro_projeto.model.Fornecedor;
import com.exemplo.meu_primeiro_projeto.repository.FornecedorRepository;

@ExtendWith(MockitoExtension.class)
public class FornecedorServiceTest {

    @Mock
    private FornecedorRepository repository;

    @Mock
    private FornecedorMapper mapper;

    @InjectMocks
    private FornecedorService service;

    @Test
    void criarFornecedor_deveCriarComSucesso() {
        FornecedorRequest request = criarRequestPadrao();
        Fornecedor fornecedor = criarFornecedorPadrao();
        FornecedorResponse response = criarFornecedorResponsePadrao();


        when(repository.existsByCnpj(request.cnpj()))
            .thenReturn(false);

        when(mapper.toEntity(request))
            .thenReturn(fornecedor);

        when(repository.save(any(Fornecedor.class)))
            .thenReturn(fornecedor);

        when(mapper.toResponse(fornecedor))
            .thenReturn(response);


        FornecedorResponse resposta = service.criarFornecedor(request);


        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.cnpj(), resposta.cnpj());
        assertEquals(request.telefone(), resposta.telefone());


        verify(repository).existsByCnpj(request.cnpj());
        verify(mapper).toEntity(request);
        verify(repository).save(any(Fornecedor.class));
        verify(mapper).toResponse(fornecedor);
    }
    
    @Test
    void criarFornecedor_deveLancarExcecaoQuandoCnpjJaExiste() {
        FornecedorRequest request = criarRequestPadrao();


        when(repository.existsByCnpj(request.cnpj()))
            .thenReturn(true);


        assertThrows(
            CnpjJaCadastradoException.class,
            () -> service.criarFornecedor(request)
        );

        verify(repository).existsByCnpj(request.cnpj());
        verify(repository, never()).save(any(Fornecedor.class));
    }

    @Test
    void listarFornecedores_deveRetornarLista() {
        Fornecedor fornecedor1 = criarFornecedorPadrao();

        Fornecedor fornecedor2 = new Fornecedor(
            "Coca-Cola Brasil",
            "98765432100000",
            "88888-8888"
        );
        fornecedor2.setId(2L);

        FornecedorResponse response1 = criarFornecedorResponsePadrao();

        FornecedorResponse response2 = new FornecedorResponse(
            2L,
            fornecedor2.getNome(),
            fornecedor2.getCnpj(),
            fornecedor2.getTelefone()
        );

        when(repository.findAll())
            .thenReturn(List.of(fornecedor1, fornecedor2));

        when(mapper.toResponse(fornecedor1))
            .thenReturn(response1);

        when(mapper.toResponse(fornecedor2))
            .thenReturn(response2);


        List<FornecedorResponse> resposta = service.listarFornecedores();


        assertEquals(2, resposta.size());

        assertEquals(fornecedor1.getNome(), resposta.get(0).nome());
        assertEquals(fornecedor1.getCnpj(), resposta.get(0).cnpj());
        assertEquals(fornecedor1.getTelefone(), resposta.get(0).telefone());

        assertEquals(fornecedor2.getNome(), resposta.get(1).nome());
        assertEquals(fornecedor2.getCnpj(), resposta.get(1).cnpj());
        assertEquals(fornecedor2.getTelefone(), resposta.get(1).telefone());


        verify(repository).findAll();
        verify(mapper).toResponse(fornecedor1);
        verify(mapper).toResponse(fornecedor2);
    }

    @Test
    void listarFornecedoresAtivos_deveRetornarLista() {
        Fornecedor fornecedor = criarFornecedorPadrao();
        FornecedorResponse response = criarFornecedorResponsePadrao();

        when(repository.findByAtivoTrue())
            .thenReturn(List.of(fornecedor));

        when(mapper.toResponse(fornecedor))
            .thenReturn(response);


        List<FornecedorResponse> resposta = service.listarFornecedoresAtivos();


        assertEquals(1, resposta.size());

        assertEquals(fornecedor.getNome(), resposta.get(0).nome());
        assertEquals(fornecedor.getCnpj(), resposta.get(0).cnpj());


        verify(repository).findByAtivoTrue();
        verify(mapper).toResponse(fornecedor);
    }

    @Test
    void buscarPorId_deveRetornarFornecedorQuandoExistir() {
        Fornecedor fornecedor = criarFornecedorPadrao();
        FornecedorResponse response = criarFornecedorResponsePadrao();

        when(repository.findById(fornecedor.getId()))
            .thenReturn(Optional.of(fornecedor));

        when(mapper.toResponse(fornecedor))
            .thenReturn(response);


        FornecedorResponse resposta = service.buscarPorId(fornecedor.getId());


        assertEquals(fornecedor.getId(), resposta.id());
        assertEquals(fornecedor.getNome(), resposta.nome());
        assertEquals(fornecedor.getCnpj(), resposta.cnpj());
        assertEquals(fornecedor.getTelefone(), resposta.telefone());


        verify(repository).findById(fornecedor.getId());
        verify(mapper).toResponse(fornecedor);
    }

    @Test
    void buscarPorId_deveLancarExcecaoQuandoFornecedorNaoExistir() {
        Long id = 1L;


        when(repository.findById(id))
            .thenReturn(Optional.empty());


        assertThrows(
            FornecedorNaoEncontradoException.class,
            () -> service.buscarPorId(id)
        );


        verify(repository).findById(id);
    }

    @Test
    void atualizarFornecedor_deveAtualizarComSucesso() {
        Fornecedor fornecedor = criarFornecedorPadrao();

        FornecedorRequest request = new FornecedorRequest(
            "Fornecedor Atualizado",
            "22222222000100",
            "99999-9999"
        );

        FornecedorResponse response = new FornecedorResponse(
            fornecedor.getId(),
            request.nome(),
            request.cnpj(),
            request.telefone()
        );


        when(repository.findById(fornecedor.getId()))
            .thenReturn(Optional.of(fornecedor));

        when(repository.existsByCnpj(request.cnpj()))
            .thenReturn(false);

        when(repository.save(any(Fornecedor.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        when(mapper.toResponse(fornecedor))
            .thenReturn(response);


        FornecedorResponse resposta = service.atualizarFornecedor(
            fornecedor.getId(),
            request
        );


        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.cnpj(), resposta.cnpj());
        assertEquals(request.telefone(), resposta.telefone());


        verify(repository).findById(fornecedor.getId());
        verify(repository).existsByCnpj(request.cnpj());
        verify(repository).save(any(Fornecedor.class));
        verify(mapper).toResponse(fornecedor);
    }

    @Test
    void atualizarFornecedor_deveLancarExcecaoQuandoFornecedorNaoExistir() {
        Long id = 1L;

        FornecedorRequest request = criarRequestPadrao();


        when(repository.findById(id))
            .thenReturn(Optional.empty());


        assertThrows(
            FornecedorNaoEncontradoException.class,
            () -> service.atualizarFornecedor(id, request)
        );


        verify(repository).findById(id);
        verify(repository, never()).existsByCnpj(request.cnpj());
        verify(repository, never()).save(any(Fornecedor.class));
    }

    @Test
    void atualizarFornecedor_deveLancarExcecaoQuandoNovoCnpjJaExiste() {
        Fornecedor fornecedor = criarFornecedorPadrao();

        FornecedorRequest request = new FornecedorRequest(
            "Fornecedor Novo",
            "22222222000100",
            "99999-9999"
        );


        when(repository.findById(fornecedor.getId()))
            .thenReturn(Optional.of(fornecedor));


        when(repository.existsByCnpj(request.cnpj()))
            .thenReturn(true);


        assertThrows(
            CnpjJaCadastradoException.class,
            () -> service.atualizarFornecedor(
                fornecedor.getId(),
                request
            )
        );

        verify(repository).findById(fornecedor.getId());
        verify(repository).existsByCnpj(request.cnpj());
        verify(repository, never()).save(any(Fornecedor.class));
    }

    @Test
    void encerrarFornecedor_deveExcluirQuandoNaoPossuiCompras() {
        Fornecedor fornecedor = criarFornecedorPadrao();

        when(repository.findById(fornecedor.getId()))
            .thenReturn(Optional.of(fornecedor));


        service.encerrarFornecedor(fornecedor.getId());

        verify(repository).findById(fornecedor.getId());
        verify(repository).delete(fornecedor);
        verify(repository, never()).save(any(Fornecedor.class));
    }

    @Test
    void encerrarFornecedor_deveDesativarQuandoPossuiCompras() {
        Fornecedor fornecedor = criarFornecedorPadrao();

        Compra compra = new Compra(fornecedor);
        fornecedor.getCompras().add(compra);


        when(repository.findById(fornecedor.getId()))
            .thenReturn(Optional.of(fornecedor));


        service.encerrarFornecedor(fornecedor.getId());


        assertEquals(false, fornecedor.getAtivo());


        verify(repository).findById(fornecedor.getId());
        verify(repository).save(fornecedor);
        verify(repository, never()).delete(fornecedor);
    }

    @Test
    void encerrarFornecedor_deveLancarExcecaoQuandoFornecedorNaoExistir() {
        Long id = 1L;

        when(repository.findById(id))
            .thenReturn(Optional.empty());


        assertThrows(
            FornecedorNaoEncontradoException.class,
            () -> service.encerrarFornecedor(id)
        );

        verify(repository).findById(id);
        verify(repository, never()).delete(any(Fornecedor.class));
        verify(repository, never()).save(any(Fornecedor.class));
    }

    private Fornecedor criarFornecedorPadrao() {
        Fornecedor fornecedor = new Fornecedor(
            "Ambev",
            "12345678000100",
            "77777-7777"
        );

        fornecedor.setId(1L);

        return fornecedor;
    }

    private FornecedorRequest criarRequestPadrao() {
        return new FornecedorRequest(
            "Ambev",
            "12345678000100",
            "77777-7777"
        );
    }

    private FornecedorResponse criarFornecedorResponsePadrao() {
        Fornecedor fornecedor = criarFornecedorPadrao();

        return new FornecedorResponse(
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getCnpj(),
            fornecedor.getTelefone()
        );
    }
}