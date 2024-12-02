package br.ufg.inf.backend.spring_cat_tag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufg.inf.backend.spring_cat_tag.model.Tag;
import br.ufg.inf.backend.spring_cat_tag.repository.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;

	public List<Tag> listarTag() {
		return tagRepository.findAll();
	}

	public Tag salvarTag(Tag tag) {
		return tagRepository.save(tag);
	}

	public Optional<Tag> obterTags(Long id) {
		return tagRepository.findById(id);
	}

	public void deletarTag(Long id) {
		tagRepository.deleteById(id);
	}

}
