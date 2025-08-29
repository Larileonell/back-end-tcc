package com.tcc.pedido_service.service;

import com.tcc.pedido_service.dto.ProdutoDTO;

import com.tcc.pedido_service.dto.UsuarioDTO;
import com.tcc.pedido_service.model.Pedido;
import com.tcc.pedido_service.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.user-service.url}")
    private String userServiceUrl;

    @Value("${app.produto-service.url}")
    private String produtoServiceUrl;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido create(Pedido pedido) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication != null ? (String) authentication.getCredentials() : null;

        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        HttpEntity<Void> entity = new HttpEntity<>(headers);


        ResponseEntity<ProdutoDTO> produtoResponse = restTemplate.exchange(
                produtoServiceUrl + "/produtos/" + pedido.getProdutoId(),
                HttpMethod.GET,
                entity,
                ProdutoDTO.class
        );
        ProdutoDTO produto = produtoResponse.getBody();
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado: " + pedido.getProdutoId());
        }


        ResponseEntity<UsuarioDTO> userResponse = restTemplate.exchange(
                userServiceUrl + "/users/" + pedido.getUserId(),
                HttpMethod.GET,
                entity,
                UsuarioDTO.class
        );
       UsuarioDTO usuario = userResponse.getBody();
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado: " + pedido.getUserId());
        }


        double total = produto.getPreco() * pedido.getQuantidade();
        pedido.setValorTotal(total);

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