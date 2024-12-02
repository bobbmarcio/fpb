package br.ufg.inf.espec.backend.aula_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.espec.backend.aula_spring.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
