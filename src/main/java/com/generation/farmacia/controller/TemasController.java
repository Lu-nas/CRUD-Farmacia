package com.generation.farmacia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.model.Temas;
import com.generation.farmacia.repository.TemasRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemasController {

    @Autowired
    private TemasRepository temasRepository;

    @GetMapping
    public ResponseEntity<List<Temas>> getAll() {
        return ResponseEntity.ok(temasRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Temas> getById(@PathVariable Long id) {
        return temasRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Temas> createTema(@RequestBody Temas tema) {
        return ResponseEntity.status(HttpStatus.CREATED).body(temasRepository.save(tema));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Temas> updateTema(@PathVariable Long id, @RequestBody Temas tema) {
        return temasRepository.findById(id)
                .map(resposta -> {
                    tema.setId(id);
                    return ResponseEntity.ok(temasRepository.save(tema));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Temas> tema = temasRepository.findById(id);

		if (tema.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		temasRepository.deleteById(id);
	}
}

