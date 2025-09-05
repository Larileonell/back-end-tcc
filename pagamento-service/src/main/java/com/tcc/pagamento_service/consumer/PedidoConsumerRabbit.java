package com.tcc.pagamento_service.consumer;



import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.event.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.producer.PagamentoProducerRabbit;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumerRabbit {
    private final PagamentoProcessor processor;
    private final PagamentoProducerRabbit producer;

    public PedidoConsumerRabbit(PagamentoProcessor processor, PagamentoProducerRabbit producer) {
        this.processor = processor;
        this.producer = producer;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.pedido-criado}")
    public void consume(PedidoCriadoEvent pedido) {
        Pagamento pagamento = processor.criarPagamento(pedido);
        System.out.println("💾 Pagamento criado como PENDENTE para pedido " + pedido.getId());


        PagamentoProcessadoEvent evento = processor.processar(pagamento);


        producer.enviar(evento);
        System.out.println("📤 Pagamento processado enviado para RabbitMQ: " + evento);
    }
}
