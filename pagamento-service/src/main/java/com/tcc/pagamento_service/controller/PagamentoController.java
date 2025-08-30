package com.tcc.pagamento_service.controller;

import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.event.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final PagamentoRepository pagamentoRepository;
    private final PagamentoProcessor pagamentoProcessor;

    public PagamentoController(PagamentoRepository pagamentoRepository, PagamentoProcessor pagamentoProcessor) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoProcessor = pagamentoProcessor;
    }

    @PostMapping
    public ResponseEntity<PagamentoProcessadoEvent> criar(@RequestBody PedidoCriadoEvent pedido) {
        PagamentoProcessadoEvent evento = pagamentoProcessor.processar(pedido);
        return ResponseEntity.ok(evento);
    }

    @GetMapping
    public List<Pagamento> listar(){
        return pagamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> get (@PathVariable Long id){
        return pagamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<Pagamento> byPedido(@PathVariable Long pedidoId) {
        return pagamentoRepository.findByPedidoId(pedidoId);
    }
}
