package com.tcc.pedido_service.controller;

import com.tcc.pedido_service.dto.PedidoDTO;
import com.tcc.pedido_service.model.Pedido;
import com.tcc.pedido_service.repository.PedidoRepository;
import com.tcc.pedido_service.service.PedidoService;
import jakarta.persistence.Entity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoDTO dto){
        Pedido pedido = new Pedido();
        pedido.setUserId(dto.getUserId());
        pedido.setProdutoId(dto.getProdutoId());
        pedido.setQuantidade(dto.getQuantidade());
        pedido.setValorTotal(100.0);
        return ResponseEntity.ok(pedidoService.create(pedido));
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
