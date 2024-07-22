package com.generation.farmacia.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.farmacia.model.Categoria;

import com.generation.farmacia.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	
	@Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Categoria> getCategoriaById(@PathVariable Long id) {
        return categoriaService.findById(id);
    }

    @PostMapping
    public Categoria createCategoria(@RequestBody Categoria categoria) {
        return categoriaService.save(categoria);
    }

    @PutMapping("/{id}")
    public Categoria updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        return categoriaService.save(categoria);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteById(id);
    }
	
		
}
