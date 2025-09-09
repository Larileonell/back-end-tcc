package com.tcc.produto_service.service;

import com.tcc.produto_service.model.Produto;
import com.tcc.produto_service.repository.ProdutoRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final Counter produtosRequests;
    private final Timer produtosTimer;

    public ProdutoService(ProdutoRepository produtoRepository, MeterRegistry registry) {
        this.produtoRepository = produtoRepository;
        this.produtosRequests = registry.counter("produtos.requests.total");
        this.produtosTimer = registry.timer("produtos.request.duration");
    }

    public Produto save(Produto produto) {
        produtosRequests.increment();
        return produtosTimer.record(() -> produtoRepository.save(produto));
    }

    public List<Produto> findAll() {
        produtosRequests.increment();
        return produtosTimer.record(() -> produtoRepository.findAll());
    }

    public Optional<Produto> findById(Long id) {
        produtosRequests.increment();
        return produtosTimer.record(() -> produtoRepository.findById(id));
    }

    public void deleteById(Long id) {
        produtosRequests.increment();
        produtosTimer.record(() -> produtoRepository.deleteById(id));
    }
}
