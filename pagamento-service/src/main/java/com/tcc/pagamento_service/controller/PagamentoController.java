package com.tcc.pagamento_service.controller;

import com.tcc.pagamento_service.dto.PagamentoRequest;
import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoProcessor processor;
    private final PagamentoRepository repository;
    private final RestTemplate restTemplate;

    @Value("${app.pedido-service.url}")
    private String pedidoServiceUrl;

    public PagamentoController(PagamentoProcessor processor,
                               PagamentoRepository repository) {
        this.processor = processor;
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping
    public ResponseEntity<PagamentoProcessadoEvent> pagar(@RequestBody PagamentoRequest request) {


        PedidoResponse pedido = restTemplate.getForObject(
                pedidoServiceUrl + "/pedidos/" + request.getPedidoId(),
                PedidoResponse.class
        );

        if (pedido == null) {
            return ResponseEntity.badRequest().build();
        }


        Pagamento pagamento = processor.criarPagamento(request, pedido.getValorTotal());

        PagamentoProcessadoEvent evento = processor.processar(pagamento, pedido.getValorTotal());

        return ResponseEntity.ok(evento);
    }

    @GetMapping
    public List<Pagamento> listar() {
        return repository.findAll();
    }

    static class PedidoResponse {
        private BigDecimal valorTotal; // já BigDecimal
        public BigDecimal getValorTotal() { return valorTotal; }
        public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    }

}
