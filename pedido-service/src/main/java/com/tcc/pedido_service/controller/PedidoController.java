package com.tcc.pedido_service.controller;

import com.tcc.pedido_service.dto.PedidoDTO;
import com.tcc.pedido_service.events.PedidoEventPublisher;
import com.tcc.pedido_service.model.Pedido;
import com.tcc.pedido_service.repository.PedidoRepository;
import com.tcc.pedido_service.service.PedidoService;
import com.tcc.produto_service.model.Produto;
import com.tcc.produto_service.service.ProdutoService;
import jakarta.persistence.Entity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final PedidoEventPublisher publisher;
    private final ProdutoService produtoService; // 🔥 precisa injetar

    public PedidoController(PedidoService pedidoService,
                            PedidoEventPublisher publisher,
                            ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.publisher = publisher;
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Pedido pedido = new Pedido();

        if (auth != null && auth.getPrincipal() instanceof UserDetails userDetails) {
            pedido.setUserId(Long.valueOf(userDetails.getUsername()));
        } else {
            pedido.setUserId(dto.getUserId());
        }

        pedido.setProdutoId(dto.getProdutoId());
        pedido.setQuantidade(dto.getQuantidade());

        // 🔥 agora calcula com base no preçoUnitario recebido
        pedido.setValorTotal(dto.getPrecoUnitario() * dto.getQuantidade());

        Pedido salvo = pedidoService.create(pedido);

        // 🔥 Publica evento
        publisher.publishPedidoCriado(salvo);

        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

}
