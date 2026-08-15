package com.exemplo.meu_primeiro_projeto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.response.VendaResponse;
import com.exemplo.meu_primeiro_projeto.exception.CarrinhoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.exception.CarrinhoVazioException;
import com.exemplo.meu_primeiro_projeto.exception.VendaNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.mapper.VendaMapper;
import com.exemplo.meu_primeiro_projeto.model.Carrinho;
import com.exemplo.meu_primeiro_projeto.model.ItemCarrinho;
import com.exemplo.meu_primeiro_projeto.model.ItemVenda;
import com.exemplo.meu_primeiro_projeto.model.Venda;
import com.exemplo.meu_primeiro_projeto.repository.CarrinhoRepository;
import com.exemplo.meu_primeiro_projeto.repository.VendaRepository;

import jakarta.transaction.Transactional;


@Service
public class VendaService {
    private final VendaRepository vendaRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final EstoqueService estoqueService;
    private final CalculoPrecoService calculoPrecoService;
    private final VendaMapper mapper;

    public VendaService(VendaRepository vendaRepository, CarrinhoRepository carrinhoRepository, EstoqueService estoqueService, CalculoPrecoService calculoPrecoService, VendaMapper mapper) {
        this.vendaRepository = vendaRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.estoqueService = estoqueService;
        this.calculoPrecoService = calculoPrecoService;
        this.mapper = mapper;
    }

    @Transactional
    public VendaResponse realizarVenda(Long idCarrinho) {
        Carrinho carrinho = verificarCarrinho(idCarrinho);
        if(carrinho.getItens().isEmpty()) {
            throw new CarrinhoVazioException("O carrinho está vazio.");
        }

        Venda venda = new Venda(
            carrinho.getCliente()
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

    public List<VendaResponse> listarVendas() {
        return vendaRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    private Venda verificarVenda(Long idVenda) {
        return vendaRepository.findById(idVenda)
                .orElseThrow(() -> new VendaNaoEncontradaException("Venda não encontrada."));
    }

    private Carrinho verificarCarrinho(Long idCarrinho) {
        return carrinhoRepository.findById(idCarrinho)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException("Carrinho não encontrado."));
    }
}