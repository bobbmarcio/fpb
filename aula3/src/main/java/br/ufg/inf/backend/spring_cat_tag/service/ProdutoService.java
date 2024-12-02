package br.ufg.inf.backend.spring_cat_tag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufg.inf.backend.spring_cat_tag.model.Produto;
import br.ufg.inf.backend.spring_cat_tag.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<Produto> listarProdutos() {
		return produtoRepository.findAll();
	}

	public Produto salvarProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

	public Optional<Produto> obterProdutos(Long id) {
		return produtoRepository.findById(id);
	}

	public void deletarProduto(Long id) {
		produtoRepository.deleteById(id);
	}
	
}