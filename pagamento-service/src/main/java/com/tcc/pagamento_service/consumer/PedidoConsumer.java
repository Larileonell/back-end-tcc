package com.tcc.pagamento_service.consumer;

import com.tcc.pagamento_service.dto.PedidoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {
    @RabbitListener(queues = "${app.rabbitmq.queue.pedido-criado}")
    public void receberPedido(PedidoDTO pedido) {
        System.out.println("📥 Pedido recebido no pagamento-service: " + pedido);

    }
}
