package com.generation.farmacia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.farmacia.model.Temas;
import com.generation.farmacia.repository.TemasRepository;

@Service
public class TemasService {

	@Autowired
	private TemasRepository temasRepository;

	public List<Temas> findAll() {
		return temasRepository.findAll();
	}

	public Optional<Temas> findById(Long id) {
		return temasRepository.findById(id);
	}

	public Temas save(Temas categoria) {
		return temasRepository.save(categoria);
	}

	public void deleteById(Long id) {
		temasRepository.deleteById(id);
	}
}
