package com.tcc.pedido_service.service;

import com.tcc.pedido_service.dto.ProdutoDTO;


import com.tcc.pedido_service.model.Pedido;
import com.tcc.pedido_service.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    @Value("${app.produto-service.url}")
    private String produtoServiceUrl;

    private final WebClient webClient = WebClient.builder().build();

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido create(Pedido pedido) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        String token = (String) authentication.getCredentials();

        ProdutoDTO produto = webClient.get()
                .uri(produtoServiceUrl + "/produtos/{id}", pedido.getProdutoId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ProdutoDTO.class)
                .block(); // síncrono e simples

        if (produto == null) {
            throw new RuntimeException("Produto não encontrado: " + pedido.getProdutoId());
        }

        double total = produto.getPreco() * pedido.getQuantidade();
        pedido.setValorTotal(total);
        pedido.setUserId(userId);

        return pedidoRepository.save(pedido);
    }
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<Pedido> listarPorUsuario(Long userId) {
        return pedidoRepository.findByUserId(userId);
    }
}