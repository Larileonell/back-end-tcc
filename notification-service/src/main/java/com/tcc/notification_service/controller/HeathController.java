package com.tcc.notification_service.controller;


import com.tcc.notification_service.dto.NotificationDTO;
import com.tcc.notification_service.model.Notification;
import com.tcc.notification_service.repository.NotificationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notificacoes")
public class HeathController {
    private final NotificationRepository repository;

    public HeathController(NotificationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Notification> listar() {
        return repository.findAll();
    }

    @GetMapping("/{pedidoId}")
    public List<NotificationDTO> listarPorPedido(@PathVariable Long pedidoId) {
        return repository.findByPedidoId(pedidoId)
                .stream()
                .map(n -> new NotificationDTO(
                        n.getPedidoId(),
                        n.getTipo(),
                        n.getStatus(),
                        n.getValorTotal(),
                        n.getMensagem(),
                        n.getCanal()
                ))
                .collect(Collectors.toList());
    }
}
