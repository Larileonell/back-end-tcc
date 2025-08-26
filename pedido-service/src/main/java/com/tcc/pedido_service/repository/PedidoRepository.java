package com.tcc.pedido_service.repository;

import com.tcc.pedido_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository  extends JpaRepository<Pedido,Long> {
}
