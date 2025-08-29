package com.tcc.pedido_service.repository;

import com.tcc.pedido_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository  extends JpaRepository<Pedido,Long> {
    List<Pedido> findByUserId(Long userId);
}
