package com.exemplo.meu_primeiro_projeto.procurement.service;

import com.exemplo.meu_primeiro_projeto.catalog.model.Produto;
import com.exemplo.meu_primeiro_projeto.catalog.repository.ProdutoRepository;
import com.exemplo.meu_primeiro_projeto.inventory.service.EstoqueService;
import com.exemplo.meu_primeiro_projeto.procurement.dto.filter.CompraFiltro;
import com.exemplo.meu_primeiro_projeto.procurement.dto.request.CompraRequest;
import com.exemplo.meu_primeiro_projeto.procurement.dto.response.CompraResponse;
import com.exemplo.meu_primeiro_projeto.procurement.mapper.CompraMapper;
import com.exemplo.meu_primeiro_projeto.procurement.model.Compra;
import com.exemplo.meu_primeiro_projeto.procurement.model.Fornecedor;
import com.exemplo.meu_primeiro_projeto.procurement.model.ItemCompra;
import com.exemplo.meu_primeiro_projeto.procurement.repository.CompraRepository;

import com.exemplo.meu_primeiro_projeto.procurement.repository.FornecedorRepository;
import com.exemplo.meu_primeiro_projeto.procurement.repository.specification.CompraSpecification;

import com.exemplo.meu_primeiro_projeto.shared.exception.ResourceNotFoundException;
import com.exemplo.meu_primeiro_projeto.shared.service.CalculoPrecoService;
import jakarta.transaction.Transactional;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final FornecedorRepository fornecedorRepository;
    private final CompraRepository compraRepository;
    private final EstoqueService estoqueService;
    private final CalculoPrecoService calculoPrecoService;
    private final ProdutoRepository produtoRepository;
    private final CompraMapper mapper;

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

    public Page<CompraResponse> listarCompras(CompraFiltro filtro, Pageable pageable) {
        return compraRepository.findAll(CompraSpecification.comFiltro(filtro), pageable)
                .map(mapper::toResponse);
    }

    private Compra verificarCompra(Long idCompra) {
        return compraRepository.findById(idCompra)
                .orElseThrow(() -> new ResourceNotFoundException("Compra não encontrada"));
    }

    private Fornecedor verificarFornecedor(Long idFornecedor) {
        return fornecedorRepository.findById(idFornecedor)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));
    }

    private Produto verificarProduto(Long idProduto) {
        return produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }
}