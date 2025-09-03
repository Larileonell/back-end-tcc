package com.tcc.notification_service.repository;

import com.tcc.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByPedidoId(Long pedidoId);
}
