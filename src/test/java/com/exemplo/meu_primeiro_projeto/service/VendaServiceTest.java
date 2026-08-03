package com.exemplo.meu_primeiro_projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import com.exemplo.meu_primeiro_projeto.dto.VendaResponse;
import com.exemplo.meu_primeiro_projeto.exception.CarrinhoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.exception.CarrinhoVazioException;
import com.exemplo.meu_primeiro_projeto.exception.VendaNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.model.Carrinho;
import com.exemplo.meu_primeiro_projeto.model.Categoria;
import com.exemplo.meu_primeiro_projeto.model.Cliente;
import com.exemplo.meu_primeiro_projeto.model.ItemCarrinho;
import com.exemplo.meu_primeiro_projeto.model.ItemVenda;
import com.exemplo.meu_primeiro_projeto.model.Produto;
import com.exemplo.meu_primeiro_projeto.model.Venda;
import com.exemplo.meu_primeiro_projeto.repository.CarrinhoRepository;
import com.exemplo.meu_primeiro_projeto.repository.VendaRepository;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

    @Mock
    VendaRepository vendaRepository;

    @Mock
    CarrinhoRepository carrinhoRepository;
    
    @Mock
    EstoqueService estoqueService;

    @Mock
    CalculoPrecoService calculoPrecoService;

    @InjectMocks
    VendaService service;


    @Test
    void realizarVenda_deveCriarVendaComSucesso() {
        Produto produto = criarProdutoPadrao();

        BigDecimal precoTotalVenda = produto.getPreco().multiply(BigDecimal.valueOf(2));

        Carrinho carrinho = criarCarrinhoPadrao();

        when(carrinhoRepository.findById(carrinho.getId()))
            .thenReturn(Optional.of(carrinho));

        when(calculoPrecoService.calcularValorTotalVenda(any()))
            .thenReturn(precoTotalVenda);
            
        when(vendaRepository.save(any(Venda.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        when(carrinhoRepository.save(any(Carrinho.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        int quantidadeItens = carrinho.getItens().size();

        VendaResponse resposta = service.realizarVenda(carrinho.getId());

        assertEquals(carrinho.getCliente().getId(), resposta.clienteId());
        assertEquals(precoTotalVenda, resposta.valorTotal());
        assertEquals(quantidadeItens, resposta.itens().size());

        assertTrue(carrinho.getItens().isEmpty());

        verify(carrinhoRepository).findById(carrinho.getId());
        verify(calculoPrecoService).calcularValorTotalVenda(any());
        verify(vendaRepository).save(any(Venda.class));
        verify(carrinhoRepository).save(any(Carrinho.class));
        verify(estoqueService).baixarEstoque(any(Produto.class), eq(2));
    }

    @Test
    void realizarVenda_deveLancarExcecaoQuandoCarrinhoEstiverVazio() {

        Carrinho carrinho = new Carrinho(criarClientePadrao());
        carrinho.setId(1L);

        Long carrinhoId = carrinho.getId();

        when(carrinhoRepository.findById(carrinhoId))
            .thenReturn(Optional.of(carrinho));

        assertThrows(
            CarrinhoVazioException.class,
            () -> service.realizarVenda(carrinhoId)
        );

        verify(carrinhoRepository).findById(carrinhoId);
        verify(calculoPrecoService, never()).calcularValorTotalVenda(any());
        verify(vendaRepository, never()).save(any(Venda.class));
        verify(carrinhoRepository, never()).save(any(Carrinho.class));
        verify(estoqueService, never()).baixarEstoque(any(Produto.class), anyInt());
    }

    @Test
    void realizarVenda_deveLancarExcecaoQuandoCarrinhoNaoExistir() {

        Long carrinhoId = 1L;

        when(carrinhoRepository.findById(carrinhoId))
            .thenReturn(Optional.empty());

        assertThrows(
            CarrinhoNaoEncontradoException.class,
            () -> service.realizarVenda(carrinhoId)
        );

        verify(carrinhoRepository).findById(carrinhoId);
        verify(vendaRepository, never()).save(any(Venda.class));
        verify(carrinhoRepository, never()).save(any(Carrinho.class));
        verify(estoqueService, never()).baixarEstoque(any(Produto.class), anyInt());
        verify(calculoPrecoService, never()).calcularValorTotalVenda(any());
    }

    @Test
    void buscarVenda_deveRetornarVendaQuandoExistir() {
        Venda venda = criarVendaPadrao();
        
        when(vendaRepository.findById(venda.getId()))
            .thenReturn(Optional.of(venda));

        VendaResponse resposta = service.buscarVenda(venda.getId());

        assertEquals(venda.getId(), resposta.id());
        assertEquals(venda.getCliente().getId(), resposta.clienteId());
        assertEquals(venda.getValorTotal(), resposta.valorTotal());
        assertEquals(venda.getItens().size(), resposta.itens().size());

        verify(vendaRepository).findById(venda.getId());
    }

    @Test
    void buscarVenda_deveLancarExcecaoQuandoVendaNaoExistir() {

        Long vendaId = 1L;

        when(vendaRepository.findById(vendaId))
            .thenReturn(Optional.empty());

        assertThrows(
            VendaNaoEncontradaException.class,
            () -> service.buscarVenda(vendaId)
        );

        verify(vendaRepository).findById(vendaId);
    }


    @Test
    void listarVendas_deveRetornarLista() {
         Venda venda1 = criarVendaPadrao();

        Venda venda2 = criarVendaPadrao();
        venda2.setId(2L);

        when(vendaRepository.findAll())
            .thenReturn(List.of(venda1, venda2));

        List<VendaResponse> resposta = service.listarVendas();

        assertEquals(2, resposta.size());

        assertEquals(venda1.getId(), resposta.get(0).id());
        assertEquals(venda2.getId(), resposta.get(1).id());

        verify(vendaRepository).findAll();
    }


    private Cliente criarClientePadrao() {
        Cliente cliente = new Cliente(
            "João Silva",
            "joao@email.com",
            "61999999999"
        );

        cliente.setId(1L);

        return cliente;
    }

    private Categoria criarCategoriaPadrao() {
        Categoria categoria = new Categoria(
            "Bebidas",
            "Refrigerantes"
        );

        categoria.setId(1L);

        return categoria;
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

    private Carrinho criarCarrinhoPadrao() {
        Carrinho carrinho = new Carrinho(
            criarClientePadrao()
        );

        carrinho.setId(1L);

        carrinho.getItens().add(criarItemCarrinhoPadrao());

        return carrinho;
    }

    private ItemCarrinho criarItemCarrinhoPadrao() {
        Produto produto = criarProdutoPadrao();

        ItemCarrinho item = new ItemCarrinho(
            produto,
            2,
            produto.getPreco()
        );

        item.setId(1L);

        return item;
    }

    private Venda criarVendaPadrao() {
        Venda venda = new Venda(
            criarClientePadrao()
        );

        venda.setId(1L);

        ItemVenda item = criarItemVendaPadrao();

        venda.adicionarItem(item);
        venda.setValorTotal(item.getSubtotal());

        return venda;
    }

    private ItemVenda criarItemVendaPadrao() {
        Produto produto = criarProdutoPadrao();

        ItemVenda item = new ItemVenda(
            produto,
            2,
            produto.getPreco()
        );

        item.setId(1L);

        return item;
    }
}
