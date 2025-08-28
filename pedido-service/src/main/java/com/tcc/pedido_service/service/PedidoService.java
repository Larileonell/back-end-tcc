package com.tcc.pedido_service.service;

import com.tcc.pedido_service.model.Pedido;
import com.tcc.pedido_service.repository.PedidoRepository;
import com.tcc.produto_service.model.Produto;
import com.tcc.produto_service.service.ProdutoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido create(Pedido pedido) {
        // Calcula valor total só com os dados recebidos
        double total = pedido.getPrecoUnitario() * pedido.getQuantidade();
        pedido.setValorTotal(total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }
}
