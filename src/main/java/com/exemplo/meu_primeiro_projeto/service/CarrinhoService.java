package com.exemplo.meu_primeiro_projeto.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.request.ItemCarrinhoRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CarrinhoResponse;
import com.exemplo.meu_primeiro_projeto.dto.response.ItemCarrinhoResponse;
import com.exemplo.meu_primeiro_projeto.exception.CarrinhoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.exception.ClienteNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.exception.ItemCarrinhoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.exception.ProdutoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.model.Carrinho;
import com.exemplo.meu_primeiro_projeto.model.Cliente;
import com.exemplo.meu_primeiro_projeto.model.ItemCarrinho;
import com.exemplo.meu_primeiro_projeto.model.Produto;
import com.exemplo.meu_primeiro_projeto.repository.CarrinhoRepository;
import com.exemplo.meu_primeiro_projeto.repository.ClienteRepository;
import com.exemplo.meu_primeiro_projeto.repository.ItemCarrinhoRepository;
import com.exemplo.meu_primeiro_projeto.repository.ProdutoRepository;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final EstoqueService estoqueService;
    private final CalculoPrecoService calculoPrecoService;

    public CarrinhoService(CarrinhoRepository carRepository, ClienteRepository cliRepository, ProdutoRepository produtoRepository, ItemCarrinhoRepository itemCarrinhoRepository, EstoqueService estoqueService, CalculoPrecoService calculoPrecoService) {
        this.carrinhoRepository = carRepository;
        this.clienteRepository = cliRepository;
        this.produtoRepository = produtoRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.estoqueService = estoqueService;
        this.calculoPrecoService = calculoPrecoService;
    }

    public CarrinhoResponse criarCarrinho(Long idCliente) {
        Carrinho carrinho = new Carrinho(verificarCliente(idCliente));

        carrinho = carrinhoRepository.save(carrinho);

        return carrinhoParaResponse(carrinho, BigDecimal.ZERO);
    }

    public CarrinhoResponse adicionarItem(ItemCarrinhoRequest request) {
        Carrinho carrinho = verificarCarrinho(request.carrinhoId());
        Produto produto = verificarProduto(request.produtoId());

        Optional<ItemCarrinho> itemExistente = buscarItem(carrinho, produto.getId());

        int novaQuantidade = request.quantidade();

        if (itemExistente.isPresent()) {
            novaQuantidade += itemExistente.get().getQuantidade();
        }
        
        estoqueService.verificarEstoque(produto, novaQuantidade);

        ItemCarrinho itemCarrinho;

        if(itemExistente.isPresent()) {
            itemCarrinho = itemExistente.get();
            itemCarrinho.setQuantidade(novaQuantidade);
            itemCarrinhoRepository.save(itemCarrinho);

        } else {
            itemCarrinho = criarItemCarrinho(request, produto);
            carrinho.adicionarItem(itemCarrinho);
        }

        return carrinhoParaResponse(carrinho, calculoPrecoService.calcularValorTotal(carrinho.getItens()));
    }

    public void removerItem(ItemCarrinhoRequest request) {
        Carrinho carrinho = verificarCarrinho(request.carrinhoId());
        ItemCarrinho itemCarrinho = verificarItem(carrinho, request.produtoId());

        carrinho.getItens().remove(itemCarrinho);
    }

    public CarrinhoResponse limparCarrinho(Long idCarrinho) {
        Carrinho carrinho = verificarCarrinho(idCarrinho);

        itemCarrinhoRepository.deleteAll(carrinho.getItens());
        carrinho.getItens().clear();

        return carrinhoParaResponse(carrinho, BigDecimal.ZERO);
    }

    public CarrinhoResponse alterarQuantidade(ItemCarrinhoRequest request) {
        Carrinho carrinho = verificarCarrinho(request.carrinhoId());
        ItemCarrinho item = verificarItem(carrinho, request.produtoId());

        estoqueService.verificarEstoque(item.getProduto(), request.quantidade());

        item.setQuantidade(request.quantidade());
        itemCarrinhoRepository.save(item);

        return carrinhoParaResponse(carrinho, calculoPrecoService.calcularValorTotal(carrinho.getItens()));
    }

    private ItemCarrinho criarItemCarrinho(ItemCarrinhoRequest request, Produto produto) {
        return new ItemCarrinho(
            produto,
            request.quantidade(),
            produto.getPreco()
        );
    }

    private Cliente verificarCliente(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));
    }

    private Carrinho verificarCarrinho(Long idCarrinho) {
        return carrinhoRepository.findById(idCarrinho)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException("Carrinho não encontrado."));
    }

    private Produto verificarProduto(Long idProduto) {
        return produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado."));
    }

    private ItemCarrinho verificarItem(Carrinho carrinho, Long idProduto) {
        return buscarItem(carrinho, idProduto)
                        .orElseThrow(() -> new ItemCarrinhoNaoEncontradoException("Produto não está no carrinho."));
    }

    private CarrinhoResponse carrinhoParaResponse(Carrinho carrinho, BigDecimal valorTotal) {
        List<ItemCarrinhoResponse> itens = carrinho.getItens()
            .stream()
            .map(this::itemcarrinhoParaResponse)
            .toList();

        return new CarrinhoResponse(
            carrinho.getId(),
            valorTotal,
            itens
        );
    }

    private Optional<ItemCarrinho> buscarItem(Carrinho carrinho, Long idProduto) {
        return carrinho.getItens()
                .stream()
                .filter(i -> i.getProduto().getId().equals(idProduto))
                .findFirst();
    }

    private ItemCarrinhoResponse itemcarrinhoParaResponse(ItemCarrinho item) {
        return new ItemCarrinhoResponse(
            item.getId(),
            item.getProduto().getId(),
            item.getProduto().getNome(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getSubtotal()
        );
    }
}
