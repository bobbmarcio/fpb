package br.ufg.inf.backend.spring_cat_tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.backend.spring_cat_tag.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
