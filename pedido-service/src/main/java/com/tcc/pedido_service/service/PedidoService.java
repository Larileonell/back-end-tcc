package com.tcc.pedido_service.service;

import com.tcc.pedido_service.dto.ProdutoDTO;


import com.tcc.pedido_service.model.Pedido;
import com.tcc.pedido_service.repository.PedidoRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;


@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final WebClient webClient = WebClient.builder().build();

    private final Counter pedidosCriadosCounter;
    private final Timer createPedidoTimer;

    @Value("${app.produto-service.url}")
    private String produtoServiceUrl;

    public PedidoService(PedidoRepository pedidoRepository, MeterRegistry registry) {
        this.pedidoRepository = pedidoRepository;

        this.pedidosCriadosCounter = Counter.builder("pedidos.criados")
                .description("Número de pedidos criados")
                .register(registry);

        this.createPedidoTimer = Timer.builder("pedidos.create.duration")
                .description("Tempo gasto no método create()")
                .register(registry);
    }

    public Pedido create(Pedido pedido) {
        return createPedidoTimer.record(() -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long userId = (Long) authentication.getPrincipal();
            String token = (String) authentication.getCredentials();

            ProdutoDTO produto = webClient.get()
                    .uri(produtoServiceUrl + "/produtos/{id}", pedido.getProdutoId())
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(ProdutoDTO.class)
                    .block();

            if (produto == null) {
                throw new RuntimeException("Produto não encontrado: " + pedido.getProdutoId());
            }

            double total = produto.getPreco() * pedido.getQuantidade();
            pedido.setValorTotal(total);
            pedido.setUserId(userId);

            Pedido salvo = pedidoRepository.save(pedido);
            pedidosCriadosCounter.increment();
            return salvo;
        });
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