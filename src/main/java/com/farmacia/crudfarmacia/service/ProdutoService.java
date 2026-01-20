package com.farmacia.crudfarmacia.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.farmacia.crudfarmacia.model.Produto;
import com.farmacia.crudfarmacia.repository.ProdutoRepository;



@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos(){
        return produtoRepository.findAll();
    }

    public Produto buscarPorId( Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto salvar( Produto produto){
        return produtoRepository.save(produto);
    }

    public void deletar(Long id){
        produtoRepository.deleteById(id);
    }

}

