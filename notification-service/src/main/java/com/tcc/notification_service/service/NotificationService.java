package com.tcc.notification_service.service;

import com.tcc.notification_service.dto.PagamentoProcessadoEvent;
import com.tcc.notification_service.dto.PedidoCriadoEvent;
import com.tcc.notification_service.model.Notification;
import com.tcc.notification_service.repository.NotificationRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MeterRegistry registry;

    public NotificationService(NotificationRepository notificationRepository, MeterRegistry registry) {
        this.notificationRepository = notificationRepository;
        this.registry = registry;
    }

    public void notificarPedidoCriado(PedidoCriadoEvent ev, String canal) {
        var n = new Notification();
        n.setPedidoId(ev.getId());
        n.setTipo("PEDIDO_CRIADO");
        n.setCanal(canal);
        n.setMensagem("Pedido " + ev.getId() + " criado para usuário " + ev.getUserId());
        notificationRepository.save(n);

        registry.counter("notificacoes.pedido.criado", "canal", canal).increment();
        System.out.println("🔔 [NOTIF] " + canal + " - Pedido criado: " + ev);
    }

    public void notificarPagamento(PagamentoProcessadoEvent ev, String canal) {
        var n = new Notification();
        n.setPedidoId(ev.getPedidoId());
        n.setTipo("PAGAMENTO_PROCESSADO");
        n.setCanal(canal);
        n.setStatus(ev.getStatus());
        n.setValorTotal(String.valueOf(ev.getValorTotal()));
        n.setMensagem("Pagamento do pedido " + ev.getPedidoId() +
                " = " + ev.getStatus() +
                " | Valor: " + ev.getValorTotal());
        notificationRepository.save(n);

        registry.counter("notificacoes.pagamento.processado", "canal", canal).increment();
        System.out.println("💸 [NOTIF] " + canal + " - Pagamento processado: " + ev);
    }
}