package com.exemplo.meu_primeiro_projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exemplo.meu_primeiro_projeto.dto.request.CompraRequest;
import com.exemplo.meu_primeiro_projeto.dto.request.ItemCompraRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CompraResponse;
import com.exemplo.meu_primeiro_projeto.dto.response.ItemCompraResponse;
import com.exemplo.meu_primeiro_projeto.exception.CompraNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.exception.FornecedorNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.exception.ProdutoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.mapper.CompraMapper;
import com.exemplo.meu_primeiro_projeto.model.Categoria;
import com.exemplo.meu_primeiro_projeto.model.Compra;
import com.exemplo.meu_primeiro_projeto.model.Fornecedor;
import com.exemplo.meu_primeiro_projeto.model.ItemCompra;
import com.exemplo.meu_primeiro_projeto.model.Produto;
import com.exemplo.meu_primeiro_projeto.repository.CompraRepository;
import com.exemplo.meu_primeiro_projeto.repository.FornecedorRepository;
import com.exemplo.meu_primeiro_projeto.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {
    @Mock
    CompraRepository compraRepository;

    @Mock
    FornecedorRepository fornecedorRepository;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    EstoqueService estoqueService;

    @Mock
    CalculoPrecoService calculoPrecoService;

    @Mock
    CompraMapper mapper;

    @InjectMocks
    CompraService service;

    @Test
    void realizarCompra_deveCriarCompraComSucesso() {
        CompraRequest request = criarCompraRequestPadrao();

        Long fornecedorId = request.fornecedorId();

        Fornecedor fornecedor = criarFornecedorPadrao();

        Produto produto = criarProdutoPadrao();

        ItemCompraRequest item = request.itens().getFirst();

        BigDecimal valorTotalCompra = item.precoCompra().multiply(BigDecimal.valueOf(item.quantidade()));

        CompraResponse response = criarCompraResponsePadrao();

        ItemCompra itemCompra = new ItemCompra(
            produto,
            item.quantidade(),
            item.precoCompra()
        );

        itemCompra.setId(1L);

        when(fornecedorRepository.findById(fornecedorId))
            .thenReturn(Optional.of(fornecedor));

        when(produtoRepository.findById(produto.getId()))
            .thenReturn(Optional.of(produto));

        when(mapper.toItemEntity(item, produto))
            .thenReturn(itemCompra);

        when(calculoPrecoService.calcularValorTotalCompra(any()))
            .thenReturn(valorTotalCompra);

        when(compraRepository.save(any(Compra.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        when(mapper.toResponse(any(Compra.class)))
            .thenReturn(response);

        CompraResponse resposta = service.realizarCompra(request);

        assertEquals(fornecedorId, resposta.fornecedorId());
        assertEquals(valorTotalCompra, resposta.valorTotal());
        assertEquals(request.itens().size(), resposta.itens().size());

        verify(fornecedorRepository).findById(fornecedorId);
        verify(produtoRepository).findById(produto.getId());
        verify(calculoPrecoService).calcularValorTotalCompra(any());
        verify(compraRepository).save(any(Compra.class));
        verify(estoqueService).aumentarEstoque(eq(produto), eq(item.quantidade()));
        verify(mapper).toItemEntity(item, produto);
        verify(mapper).toResponse(any(Compra.class));
    }

    @Test
    void realizarCompra_deveLancarExcecaoQuandoFornecedorNaoExistir() {

        CompraRequest request = criarCompraRequestPadrao();

        Long fornecedorId = request.fornecedorId();

        when(fornecedorRepository.findById(fornecedorId))
            .thenReturn(Optional.empty());

        assertThrows(
            FornecedorNaoEncontradoException.class,
            () -> service.realizarCompra(request)
        );

        verify(fornecedorRepository).findById(fornecedorId);
        verify(produtoRepository, never()).findById(any());
        verify(calculoPrecoService, never()).calcularValorTotalCompra(any());
        verify(compraRepository, never()).save(any());
        verify(estoqueService, never()).aumentarEstoque(any(), any(Integer.class));
    }

    @Test
    void realizarCompra_deveLancarExcecaoQuandoProdutoNaoExistir() {

        CompraRequest request = criarCompraRequestPadrao();

        Long fornecedorId = request.fornecedorId();
        ItemCompraRequest item = request.itens().getFirst();
        Long produtoId = item.produtoId();

        when(fornecedorRepository.findById(fornecedorId))
            .thenReturn(Optional.of(criarFornecedorPadrao()));

        when(produtoRepository.findById(produtoId))
            .thenReturn(Optional.empty());

        assertThrows(
            ProdutoNaoEncontradoException.class,
            () -> service.realizarCompra(request)
        );

        verify(fornecedorRepository).findById(fornecedorId);
        verify(produtoRepository).findById(produtoId);
        verify(calculoPrecoService, never()).calcularValorTotalCompra(any());
        verify(compraRepository, never()).save(any());
        verify(estoqueService, never()).aumentarEstoque(any(), any(Integer.class));
    }

    @Test
    void buscarCompra_deveRetornarCompraQuandoExistir() {
        Compra compra = criarCompraPadrao();

        Long compraId = compra.getId();

        CompraResponse response = criarCompraResponsePadrao();

        when(compraRepository.findById(compraId))
            .thenReturn(Optional.of(compra));

        when(mapper.toResponse(compra))
            .thenReturn(response);

        CompraResponse resposta = service.buscarCompra(compraId);

        assertEquals(compraId, resposta.id());
        assertEquals(compra.getFornecedor().getId(), resposta.fornecedorId());
        assertEquals(compra.getValorTotal(), resposta.valorTotal());
        assertEquals(compra.getItens().size(), resposta.itens().size());

        verify(compraRepository).findById(compraId);
        verify(mapper).toResponse(compra);
    }

    @Test
    void buscarCompra_deveLancarExcecaoQuandoCompraNaoExistir() {

        Long compraId = 1L;

        when(compraRepository.findById(compraId))
            .thenReturn(Optional.empty());

        assertThrows(
            CompraNaoEncontradaException.class,
            () -> service.buscarCompra(compraId)
        );

        verify(compraRepository).findById(compraId);
    }

    @Test
    void listarCompras_deveRetornarLista() {

        Compra compra1 = criarCompraPadrao();

        Compra compra2 = criarCompraPadrao();
        compra2.setId(2L);

        CompraResponse response1 = criarCompraResponsePadrao();

        ItemCompra item2 = compra2.getItens().getFirst();

        ItemCompraResponse itemResponse2 = new ItemCompraResponse(
            item2.getId(),
            item2.getProduto().getId(),
            item2.getProduto().getNome(),
            item2.getQuantidade(),
            item2.getPrecoCompra(),
            item2.getSubtotal()
        );

        CompraResponse response2 = new CompraResponse(
            compra2.getId(),
            compra2.getFornecedor().getId(),
            compra2.getDataCompra(),
            compra2.getValorTotal(),
            List.of(itemResponse2)
        );

        when(compraRepository.findAll())
            .thenReturn(List.of(compra1, compra2));

        when(mapper.toResponse(compra1))
            .thenReturn(response1);

        when(mapper.toResponse(compra2))
            .thenReturn(response2);

        List<CompraResponse> resposta = service.listarCompras();

        assertEquals(2, resposta.size());

        assertEquals(compra1.getId(), resposta.get(0).id());
        assertEquals(compra2.getId(), resposta.get(1).id());

        verify(compraRepository).findAll();
        verify(mapper).toResponse(compra1);
        verify(mapper).toResponse(compra2);
    }

    private Fornecedor criarFornecedorPadrao() {
        Fornecedor fornecedor = new Fornecedor(
            "Fornecedor ABC",
            "11999999999",
            "contato@fornecedor.com"
        );

        fornecedor.setId(1L);

        return fornecedor;
    }

    private Produto criarProdutoPadrao() {
        Produto produto = new Produto(
            "Coca-Cola",
            new BigDecimal("5.99"),
            "Refrigerante lata",
            10,
            criarCategoriaPadrao()
        );

        produto.setId(1L);

        return produto;
    }

    private Categoria criarCategoriaPadrao() {
        Categoria categoria = new Categoria(
            "Bebidas",
            "Refrigerantes"
        );

        categoria.setId(1L);

        return categoria;
    }

    private ItemCompraRequest criarItemCompraRequestPadrao() {
        Produto produto = criarProdutoPadrao();

        return new ItemCompraRequest(
            produto.getId(),
            2,
            produto.getPreco()
        );
    }

    private CompraRequest criarCompraRequestPadrao() {
        Fornecedor fornecedor = criarFornecedorPadrao();

        return new CompraRequest(
            fornecedor.getId(),
            List.of(criarItemCompraRequestPadrao())
        );
    }

    private ItemCompra criarItemCompraPadrao() {
        Produto produto = criarProdutoPadrao();

        ItemCompra item = new ItemCompra(
            produto,
            2,
            produto.getPreco()
        );

        item.setId(1L);

        return item;
    }

    private Compra criarCompraPadrao() {
        Compra compra = new Compra(
            criarFornecedorPadrao()
        );

        compra.setId(1L);

        ItemCompra item = criarItemCompraPadrao();

        compra.adicionarItem(item);
        compra.setValorTotal(item.getSubtotal());

        return compra;
    }

    private CompraResponse criarCompraResponsePadrao() {
        Compra compra = criarCompraPadrao();
        ItemCompra item = compra.getItens().getFirst();

        ItemCompraResponse itemResponse = new ItemCompraResponse(
            item.getId(),
            item.getProduto().getId(),
            item.getProduto().getNome(),
            item.getQuantidade(),
            item.getPrecoCompra(),
            item.getSubtotal()
        );

        return new CompraResponse(
            compra.getId(),
            compra.getFornecedor().getId(),
            compra.getDataCompra(),
            compra.getValorTotal(),
            List.of(itemResponse)
        );
    }
}