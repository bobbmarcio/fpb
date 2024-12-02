package br.ufg.inf.backend.spring_cat_tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.backend.spring_cat_tag.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
