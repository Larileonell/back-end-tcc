package com.tcc.pagamento_service.controller;

import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final PagamentoRepository pagamentoRepository;

    public PagamentoController(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @GetMapping
    public List<Pagamento> listar(){
        return pagamentoRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> get (@PathVariable Long id){
        return pagamentoRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/pedido/{pedidoId}")
    public List<Pagamento> byPedido(@PathVariable Long pedidoId) {
        return pagamentoRepository.findByPedidoId(pedidoId);
    }
}
