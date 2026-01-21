package com.farmacia.crudfarmacia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*; 
import com.farmacia.crudfarmacia.model.Produto;
import com.farmacia.crudfarmacia.service.ProdutoService;

import jakarta.validation.Valid;
 
 
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
    public ResponseEntity<Produto> buscarPorId( @PathVariable Long id){
        Produto produto = produtoService.buscarPorId(id);
        return  ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto){
        Produto salvo = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody Produto produtoAtualizado) {
            
            Produto atualizado = produtoService.atualizar(id,produtoAtualizado);
            return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }  
}
