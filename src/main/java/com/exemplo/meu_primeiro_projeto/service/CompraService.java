package com.exemplo.meu_primeiro_projeto.service;

import com.exemplo.meu_primeiro_projeto.repository.FornecedorRepository;
import com.exemplo.meu_primeiro_projeto.repository.ProdutoRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.request.CompraRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CompraResponse;
import com.exemplo.meu_primeiro_projeto.exception.CompraNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.exception.FornecedorNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.exception.ProdutoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.mapper.CompraMapper;
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
    private final CompraMapper mapper;

    public CompraService(CompraRepository compraRepository, EstoqueService estoqueService, CalculoPrecoService calculoPrecoService, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository, CompraMapper mapper) {
        this.compraRepository = compraRepository;
        this.estoqueService = estoqueService;
        this.calculoPrecoService = calculoPrecoService;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
    }

    @Transactional
    public CompraResponse realizarCompra(CompraRequest request) {
        Fornecedor fornecedor = verificarFornecedor(request.fornecedorId());

        List<ItemCompra> itens = request.itens().stream()
                                .map(itemRequest -> mapper.toItemEntity(
                                    itemRequest, verificarProduto(itemRequest.produtoId())
                                ))
                                .toList();

        Compra compra = new Compra(fornecedor);

        for(ItemCompra item : itens) {
            compra.adicionarItem(item);

            estoqueService.aumentarEstoque(item.getProduto(), item.getQuantidade());
        }

        compra.setValorTotal(calculoPrecoService.calcularValorTotalCompra(compra.getItens()));
        compraRepository.save(compra);

        return mapper.toResponse(compra);
    }

    public CompraResponse buscarCompra(Long idCompra) {
        Compra compra = verificarCompra(idCompra);

        return mapper.toResponse(compra);
    }

    public List<CompraResponse> listarCompras() {
        return compraRepository.findAll().stream()
                .map(mapper::toResponse)
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
}