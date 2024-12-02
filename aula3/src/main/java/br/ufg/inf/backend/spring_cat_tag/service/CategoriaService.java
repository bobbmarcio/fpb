package br.ufg.inf.backend.spring_cat_tag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufg.inf.backend.spring_cat_tag.model.Categoria;
import br.ufg.inf.backend.spring_cat_tag.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> listarCategoria() {
		return categoriaRepository.findAll();
	}

	public Categoria salvarCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public Optional<Categoria> obterCategorias(Long id) {
		return categoriaRepository.findById(id);
	}

	public void deletarCategoria(Long id) {
		categoriaRepository.deleteById(id);
	}

}
