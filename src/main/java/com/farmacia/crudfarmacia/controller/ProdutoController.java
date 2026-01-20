package com.farmacia.crudfarmacia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import com.farmacia.crudfarmacia.model.Produto;
import com.farmacia.crudfarmacia.service.ProdutoService;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    private final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar(){
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId( Long id){
        Produto produto = produtoService.buscarPorId(id);
         return produto != null
                ? ResponseEntity.ok(produto)
                : ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Produto> criar(Produto produto){
        Produto salvo = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar( Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
}
