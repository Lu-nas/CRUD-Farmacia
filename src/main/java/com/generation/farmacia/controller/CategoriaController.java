package com.generation.farmacia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.model.Temas;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.TemasRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private TemasRepository temaRepository;
	
	// metodo para obter todas as categorias
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	
	// obter uma categoria especifica por id
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id) {
		return categoriaRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Categoria>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(categoriaRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	// criando uma nova categoria
	@PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));

	}

	// atualizar uma categoria existente
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> put(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
		// Verifica se a categoria com o ID fornecido existe
		Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);

		if (optionalCategoria.isPresent()) {
			Categoria categoriaExistente = optionalCategoria.get();

			// Verifica se o tema associado à categoria existe
			Temas temaCategoria = categoria.getTema();
			if (temaCategoria != null && temaCategoria.getId() != null) {
				Optional<Temas> optionalTema = temaRepository.findById(temaCategoria.getId());
				
				if (optionalTema.isPresent()) {
					categoriaExistente.setTema(optionalTema.get());
				} else {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não encontrado!");
				}
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não pode ser nulo!");
			}

			// Atualiza outros campos da categoria se necessário
			categoriaExistente.setTitulo(categoria.getTitulo());
			categoriaExistente.setDescricao(categoria.getDescricao());		

			// Salva a categoria atualizada
			Categoria categoriaSalva = categoriaRepository.save(categoriaExistente);

			return ResponseEntity.ok(categoriaSalva);
		} else {
			// Se a categoria não existir, retorna NOT FOUND
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada!");
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Categoria> postagem = categoriaRepository.findById(id);

		if (postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		categoriaRepository.deleteById(id);
	}

}
