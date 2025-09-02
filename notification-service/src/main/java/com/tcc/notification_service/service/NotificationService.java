package com.tcc.notification_service.service;

import com.tcc.notification_service.dto.PagamentoProcessadoEvent;
import com.tcc.notification_service.dto.PedidoCriadoEvent;
import com.tcc.notification_service.model.Notification;
import com.tcc.notification_service.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public final NotificationRepository notificationRepository;
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void notificarPedidoCriado (PedidoCriadoEvent ev, String canal) {
        var n = new Notification();
        n.setPedidoId(ev.getId());
        n.setTipo("PEDIDO_CRIADO");
        n.setCanal(canal);
        n.setMensagem("Pedido " + ev.getId() + " criado para usuário " + ev.getUserId());
        notificationRepository.save(n);
        System.out.println("🔔 [NOTIF] " + canal + " - Pedido criado: " + ev);
    }
    public void notificarPagamento(PagamentoProcessadoEvent ev, String canal) {
        var n = new Notification();
        n.setPedidoId(ev.getPedidoId());
        n.setTipo("PAGAMENTO_PROCESSADO");
        n.setCanal(canal);
        n.setStatus(ev.getStatus());
        n.setValorTotal(String.valueOf(ev.getValorTotal()));
        n.setMensagem("Pagamento do pedido " + ev.getPedidoId() + " = " + ev.getStatus());
        notificationRepository.save(n);
        System.out.println("💸 [NOTIF] " + canal + " - Pagamento processado: " + ev);
    }
}
