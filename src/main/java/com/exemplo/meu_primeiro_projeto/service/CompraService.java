package com.exemplo.meu_primeiro_projeto.service;

import com.exemplo.meu_primeiro_projeto.repository.FornecedorRepository;
import com.exemplo.meu_primeiro_projeto.repository.ProdutoRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.CompraRequest;
import com.exemplo.meu_primeiro_projeto.dto.CompraResponse;
import com.exemplo.meu_primeiro_projeto.dto.ItemCompraRequest;
import com.exemplo.meu_primeiro_projeto.dto.ItemCompraResponse;
import com.exemplo.meu_primeiro_projeto.exception.CompraNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.exception.FornecedorNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.exception.ProdutoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.model.Compra;
import com.exemplo.meu_primeiro_projeto.model.Fornecedor;
import com.exemplo.meu_primeiro_projeto.model.ItemCompra;
import com.exemplo.meu_primeiro_projeto.model.Produto;
import com.exemplo.meu_primeiro_projeto.repository.CompraRepository;

import jakarta.transaction.Transactional;

@Service
public class CompraService {

    private final FornecedorRepository fornecedorRepository;
    private final CompraRepository compraRepository;
    private final EstoqueService estoqueService;
    private final CalculoPrecoService calculoPrecoService;
    private final ProdutoRepository produtoRepository;

    public CompraService(CompraRepository compraRepository, EstoqueService estoqueService, CalculoPrecoService calculoPrecoService, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository) {
        this.compraRepository = compraRepository;
        this.estoqueService = estoqueService;
        this.calculoPrecoService = calculoPrecoService;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public CompraResponse realizarCompra(CompraRequest request) {
        Fornecedor fornecedor = verificarFornecedor(request.fornecedorId());

        List<ItemCompra> itens = request.itens().stream()
                                .map(this::converterParaItemCompra)
                                .toList();

        Compra compra = new Compra(fornecedor);

        for(ItemCompra item : itens) {
            compra.adicionarItem(item);

            estoqueService.aumentarEstoque(item.getProduto(), item.getQuantidade());
        }

        compra.setValorTotal(calculoPrecoService.calcularValorTotalCompra(compra.getItens()));
        compraRepository.save(compra);

        return compraParaResponse(compra);
    }

    public CompraResponse buscarCompra(Long idCompra) {
        Compra compra = verificarCompra(idCompra);

        return compraParaResponse(compra);
    }

    public List<CompraResponse> listarCompras() {
        return compraRepository.findAll().stream()
                .map(this::compraParaResponse)
                .toList();
    }

    private Compra verificarCompra(Long idCompra) {
        return compraRepository.findById(idCompra)
                .orElseThrow(() -> new CompraNaoEncontradaException("Compra não encontrada"));
    }

    private Fornecedor verificarFornecedor(Long idFornecedor) {
        return fornecedorRepository.findById(idFornecedor)
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado"));
    }

    private Produto verificarProduto(Long idProduto) {
        return produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));
    }

    private CompraResponse compraParaResponse(Compra compra) {
        List<ItemCompraResponse> itens = compra.getItens().stream()
                    .map(this::itemParaResponse)
                    .toList();

        return new CompraResponse(
            compra.getId(),
            compra.getFornecedor().getId(),
            compra.getDataCompra(),
            compra.getValorTotal(),
            itens
        );
    }

    private ItemCompraResponse itemParaResponse(ItemCompra item) {
        return new ItemCompraResponse(
            item.getId(),
            item.getProduto().getId(),
            item.getProduto().getNome(),
            item.getQuantidade(),
            item.getPrecoCompra(),
            item.getSubtotal()
        );
    }

    private ItemCompra converterParaItemCompra(ItemCompraRequest request) {
        Produto produto = verificarProduto(request.produtoId());
        return new ItemCompra(
            produto,
            request.quantidade(),
            request.precoCompra()
        );
    }
}