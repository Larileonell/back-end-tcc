package com.tcc.pagamento_service.consumer;



import com.tcc.pagamento_service.dto.PagamentoRequest;
import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.event.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.producer.PagamentoProducerRabbit;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class PedidoConsumerRabbit {

    private final PagamentoProcessor processor;
    private final PagamentoProducerRabbit producer;
    private final Counter pagamentosRecebidos;
    private final Counter pagamentosAprovados;
    private final Counter pagamentosRecusados;

    public PedidoConsumerRabbit(PagamentoProcessor processor,
                                PagamentoProducerRabbit producer,
                                MeterRegistry meterRegistry) {
        this.processor = processor;
        this.producer = producer;

        this.pagamentosRecebidos = Counter.builder("pagamentos.recebidos").register(meterRegistry);
        this.pagamentosAprovados = Counter.builder("pagamentos.aprovados").register(meterRegistry);
        this.pagamentosRecusados = Counter.builder("pagamentos.recusados").register(meterRegistry);
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.pedido-criado}")
    public void consume(PedidoCriadoEvent pedido) {
        pagamentosRecebidos.increment();

        PagamentoRequest request = new PagamentoRequest(
                pedido.getId(),
                pedido.getValorTotal(),
                "PIX"
        );

        Pagamento pagamento = processor.criarPagamento(request, pedido.getValorTotal());
        PagamentoProcessadoEvent evento = processor.processar(pagamento, pedido.getValorTotal());

        if ("APROVADO".equalsIgnoreCase(evento.getStatus())) pagamentosAprovados.increment();
        else pagamentosRecusados.increment();

        producer.enviar(evento);
        System.out.println("📤 Pagamento processado enviado para RabbitMQ: " + evento);
    }
}
