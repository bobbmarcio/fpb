package br.ufg.inf.backend.spring_cat_tag.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufg.inf.backend.spring_cat_tag.model.Categoria;
import br.ufg.inf.backend.spring_cat_tag.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	

	@GetMapping
	public List<Categoria> listarCategorias() {
		return categoriaService.listarCategoria();
	}

	@PostMapping
	public Categoria adicionarCategoria(@RequestBody Categoria categoria) {
		return categoriaService.salvarCategoria(categoria);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
		Optional<Categoria> categoriaExistente = categoriaService.obterCategorias(id);
		if (categoriaExistente.isPresent()) {
			Categoria atualizado = categoriaExistente.get();
			atualizado.setNome(categoria.getNome());
			return ResponseEntity.ok(categoriaService.salvarCategoria(atualizado));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
		categoriaService.deletarCategoria(id);
		return ResponseEntity.noContent().build();
	}

}