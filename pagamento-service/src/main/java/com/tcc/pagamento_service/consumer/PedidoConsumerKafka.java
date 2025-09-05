package com.tcc.pagamento_service.consumer;


import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.event.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.producer.PagamentoProducerKafka;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumerKafka {
    private final PagamentoProcessor processor;
    private final PagamentoProducerKafka producer;

    public PedidoConsumerKafka(PagamentoProcessor processor, PagamentoProducerKafka producer) {
        this.processor = processor;
        this.producer = producer;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.pedido-criado}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "pedidoKafkaListenerContainerFactory"
    )
    public void consume(PedidoCriadoEvent pedido) {
        Pagamento pagamento = processor.criarPagamento(pedido);
        System.out.println("💾 Pagamento criado como PENDENTE para pedido " + pedido.getId());


        PagamentoProcessadoEvent evento = processor.processar(pagamento);
        producer.enviar(evento);
    }
}