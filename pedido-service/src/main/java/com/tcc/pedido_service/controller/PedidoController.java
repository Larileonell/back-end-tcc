package com.tcc.pedido_service.controller;

import com.tcc.pedido_service.dto.PedidoDTO;
import com.tcc.pedido_service.events.PedidoEventPublisher;
import com.tcc.pedido_service.model.Pedido;
import com.tcc.pedido_service.security.JwtUtil;
import com.tcc.pedido_service.service.PedidoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final PedidoEventPublisher publisher;
    private final JwtUtil jwtUtil;


    public PedidoController(PedidoService pedidoService, PedidoEventPublisher publisher, JwtUtil jwtUtil) {
        this.pedidoService = pedidoService;
        this.publisher = publisher;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.create(pedido);
        return ResponseEntity.ok(novoPedido);
    }


    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedido);
    }


    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Pedido>> listarPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(pedidoService.listarPorUsuario(userId));
    }
}