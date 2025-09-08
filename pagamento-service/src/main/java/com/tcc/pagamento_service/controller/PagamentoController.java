package com.tcc.pagamento_service.controller;

import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoRepository repository;
    private final PagamentoProcessor processor;
    private final MeterRegistry registry;

    public PagamentoController(PagamentoRepository repository,
                               PagamentoProcessor processor,
                               MeterRegistry registry) {
        this.repository = repository;
        this.processor = processor;
        this.registry = registry;
    }


    @GetMapping
    public List<Pagamento> listar() {
        return repository.findAll();
    }


    @GetMapping("/{id}")
    public Pagamento get(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }


    @GetMapping("/pedido/{pedidoId}")
    public List<Pagamento> byPedido(@PathVariable Long pedidoId) {
        return repository.findByPedidoId(pedidoId);
    }


    @PostMapping("/processar/{id}")
    public PagamentoProcessadoEvent processarManual(@PathVariable Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        PagamentoProcessadoEvent evento = processor.processar(pagamento);


        registry.counter("pagamentos.processados.manual").increment();

        return evento;
    }
}