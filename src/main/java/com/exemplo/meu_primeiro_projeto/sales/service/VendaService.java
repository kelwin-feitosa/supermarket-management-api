package com.exemplo.meu_primeiro_projeto.sales.service;

import com.exemplo.meu_primeiro_projeto.inventory.service.EstoqueService;
import com.exemplo.meu_primeiro_projeto.sales.dto.filter.VendaFiltro;
import com.exemplo.meu_primeiro_projeto.sales.dto.response.VendaResponse;
import com.exemplo.meu_primeiro_projeto.sales.mapper.VendaMapper;
import com.exemplo.meu_primeiro_projeto.sales.model.Carrinho;
import com.exemplo.meu_primeiro_projeto.sales.model.ItemCarrinho;
import com.exemplo.meu_primeiro_projeto.sales.model.ItemVenda;
import com.exemplo.meu_primeiro_projeto.sales.model.Venda;
import com.exemplo.meu_primeiro_projeto.sales.repository.CarrinhoRepository;
import com.exemplo.meu_primeiro_projeto.sales.repository.VendaRepository;
import com.exemplo.meu_primeiro_projeto.sales.repository.specification.VendaSpecification;

import com.exemplo.meu_primeiro_projeto.shared.exception.BusinessRuleException;
import com.exemplo.meu_primeiro_projeto.shared.exception.ResourceNotFoundException;
import com.exemplo.meu_primeiro_projeto.shared.service.CalculoPrecoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaRepository vendaRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final EstoqueService estoqueService;
    private final CalculoPrecoService calculoPrecoService;
    private final VendaMapper mapper;

    @Transactional
    public VendaResponse realizarVenda(Long idCarrinho) {
        Carrinho carrinho = verificarCarrinho(idCarrinho);
        if(carrinho.getItens().isEmpty()) {
            throw new BusinessRuleException("O carrinho está vazio.");
        }

        Venda venda = new Venda(
            carrinho.getUsuario()
        );

        for(ItemCarrinho itemcarrinho : carrinho.getItens()) {
            ItemVenda itemVenda = new ItemVenda(
                itemcarrinho.getProduto(),
                itemcarrinho.getQuantidade(),
                itemcarrinho.getPrecoUnitario()
            );
            
            venda.adicionarItem(itemVenda);

            estoqueService.baixarEstoque(itemcarrinho.getProduto(), itemcarrinho.getQuantidade());
        }

        venda.setValorTotal(calculoPrecoService.calcularValorTotalVenda(venda.getItens()));
        vendaRepository.save(venda);

        carrinho.getItens().clear();
        carrinhoRepository.save(carrinho);
        
        return mapper.toResponse(venda);
    }

    public VendaResponse buscarVenda(Long idVenda) {
        Venda venda = verificarVenda(idVenda);

        return mapper.toResponse(venda);
    }

    public Page<VendaResponse> listarVendas(VendaFiltro filtro, Pageable pageable) {
        return vendaRepository.findAll(VendaSpecification.comFiltro(filtro), pageable)
                .map(mapper::toResponse);
    }

    private Venda verificarVenda(Long idVenda) {
        return vendaRepository.findById(idVenda)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada."));
    }

    private Carrinho verificarCarrinho(Long idCarrinho) {
        return carrinhoRepository.findById(idCarrinho)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado."));
    }
}