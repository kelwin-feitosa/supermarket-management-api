package com.exemplo.meu_primeiro_projeto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.response.ItemVendaResponse;
import com.exemplo.meu_primeiro_projeto.dto.response.VendaResponse;
import com.exemplo.meu_primeiro_projeto.exception.CarrinhoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.exception.CarrinhoVazioException;
import com.exemplo.meu_primeiro_projeto.exception.VendaNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.model.Carrinho;
import com.exemplo.meu_primeiro_projeto.model.ItemCarrinho;
import com.exemplo.meu_primeiro_projeto.model.ItemVenda;
import com.exemplo.meu_primeiro_projeto.model.Produto;
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

    public VendaService(VendaRepository vendaRepository, CarrinhoRepository carrinhoRepository, EstoqueService estoqueService, CalculoPrecoService calculoPrecoService) {
        this.vendaRepository = vendaRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.estoqueService = estoqueService;
        this.calculoPrecoService = calculoPrecoService;
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
            Produto produto = itemcarrinho.getProduto();

            ItemVenda itemVenda = new ItemVenda(
                produto,
                itemcarrinho.getQuantidade(),
                itemcarrinho.getPrecoUnitario()
            );
            
            venda.adicionarItem(itemVenda);

            estoqueService.baixarEstoque(produto, itemcarrinho.getQuantidade());
        }

        venda.setValorTotal(calculoPrecoService.calcularValorTotalVenda(venda.getItens()));
        vendaRepository.save(venda);

        carrinho.getItens().clear();
        carrinhoRepository.save(carrinho);
        
        return vendaParaResponse(venda);
    }

    public VendaResponse buscarVenda(Long idVenda) {
        Venda venda = verificarVenda(idVenda);

        return vendaParaResponse(venda);
    }

    public List<VendaResponse> listarVendas() {
        return vendaRepository.findAll()
                .stream()
                .map(this::vendaParaResponse)
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

    private VendaResponse vendaParaResponse(Venda venda) {
        List<ItemVendaResponse> itens = venda.getItens().stream()
                            .map(this::itemParaResponse)
                            .toList();

        return new VendaResponse(
            venda.getId(),
            venda.getCliente().getId(),
            venda.getDataVenda(),
            venda.getValorTotal(),
            itens
        );
    }

    private ItemVendaResponse itemParaResponse(ItemVenda item) {
        return new ItemVendaResponse(
            item.getId(),
            item.getProduto().getId(),
            item.getProduto().getNome(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getSubtotal()
        );
    }
}
