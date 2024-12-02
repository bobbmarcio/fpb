package br.ufg.inf.backend.spring_cat_tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.backend.spring_cat_tag.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
