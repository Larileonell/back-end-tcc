package com.tcc.produto_service.service;

import com.tcc.produto_service.model.Produto;
import com.tcc.produto_service.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
   private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    public Produto save (Produto produto){
        return produtoRepository.save(produto);
    }
    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }
    public Optional<Produto> findById(Long id ){
        return produtoRepository.findById(id);
    }
    public void deleteById(Long id){
        produtoRepository.deleteById(id);
    }
}
