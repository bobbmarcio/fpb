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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufg.inf.backend.spring_cat_tag.model.Produto;
import br.ufg.inf.backend.spring_cat_tag.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	public List<Produto> listarProdutos() {
		return produtoService.listarProdutos();
	}

	@PostMapping
	public Produto adicionarProduto(@RequestBody Produto produto) {
		return produtoService.salvarProduto(produto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
		Optional<Produto> produtoExistente = produtoService.obterProdutos(id);
		if (produtoExistente.isPresent()) {
			Produto atualizado = produtoExistente.get();
			atualizado.setNome(produto.getNome());
			atualizado.setPreco(produto.getPreco());
			return ResponseEntity.ok(produtoService.salvarProduto(atualizado));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
		produtoService.deletarProduto(id);
		return ResponseEntity.noContent().build();
	}

}