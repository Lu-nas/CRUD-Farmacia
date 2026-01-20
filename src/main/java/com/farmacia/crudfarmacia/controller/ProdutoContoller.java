package com.farmacia.crudfarmacia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

import com.farmacia.crudfarmacia.model.Produto;
import com.farmacia.crudfarmacia.service.ProdutoService;

import io.micrometer.common.lang.NonNull;

@RestController
@RequestMapping("/produtos")
public class ProdutoContoller {
    
    private final ProdutoService produtoService;
    public ProdutoContoller(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar(){
        return ResponseEntity.ok(produtoService.listarToodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id){
        Produto produto = produtoService.buscarPorId(id);
         return produto != null
                ? ResponseEntity.ok(produto)
                : ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Produto> criar(@NonNull @RequestBody Produto produto){
        Produto salvo = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@NonNull @PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
}
