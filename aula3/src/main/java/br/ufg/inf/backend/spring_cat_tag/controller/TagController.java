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

import br.ufg.inf.backend.spring_cat_tag.model.Tag;
import br.ufg.inf.backend.spring_cat_tag.service.TagService;

@RestController
@RequestMapping("/tag")
public class TagController {
	
	@Autowired
	private TagService tagService;
	

	@GetMapping
	public List<Tag> listarTags() {
		return tagService.listarTag();
	}

	@PostMapping
	public Tag adicionarTag(@RequestBody Tag tag) {
		return tagService.salvarTag(tag);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tag> atualizar(@PathVariable Long id, @RequestBody Tag tag) {
		Optional<Tag> tagExistente = tagService.obterTags(id);
		if (tagExistente.isPresent()) {
			Tag atualizado = tagExistente.get();
			atualizado.setNome(tag.getNome());
			return ResponseEntity.ok(tagService.salvarTag(atualizado));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarTag(@PathVariable Long id) {
		tagService.deletarTag(id);
		return ResponseEntity.noContent().build();
	}

}
