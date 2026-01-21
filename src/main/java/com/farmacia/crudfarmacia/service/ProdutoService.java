package com.farmacia.crudfarmacia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farmacia.crudfarmacia.exception.ResourceNotFoundException;
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
        return produtoRepository.findById(id).orElseThrow(() 
        -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
        
    }

    public Produto salvar( Produto produto){
        return produtoRepository.save(produto);
    }

    public void deletar(Long id){
        Produto produto = buscarPorId(id); // lança 404 se não existir
        produtoRepository.delete(produto);
    }

    public Produto atualizar(Long id, Produto produtoAtualizado)  {
        Produto existente = produtoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: "+ id));
        
        existente.setNome(produtoAtualizado.getNome());
        existente.setDescricao(produtoAtualizado.getDescricao());
        existente.setPreco(produtoAtualizado.getPreco());
        existente.setQuantidade(produtoAtualizado.getQuantidade());

        return produtoRepository.save(existente);
    } 
}

