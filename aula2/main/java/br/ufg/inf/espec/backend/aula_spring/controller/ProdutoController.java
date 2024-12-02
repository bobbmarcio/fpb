package br.ufg.inf.espec.backend.aula_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import br.ufg.inf.espec.backend.aula_spring.model.Produto;
import br.ufg.inf.espec.backend.aula_spring.service.ProdutoService;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService; // Injeção da dependência ProdutoService

    /**
     * Exibe a lista de produtos cadastrados.
     * 
     * @param model
     * @param sucesso Mensagem de sucesso, se houver
     * @return Nome da view (produtos)
     */
    @GetMapping("/produtos")
    public String listarProdutos(Model model, @RequestParam(required = false) String sucesso) {
        model.addAttribute("produtos", produtoService.listarProdutos());
        model.addAttribute("sucesso", sucesso); // Mensagem de sucesso, caso presente
        return "produtos"; // Retorna a página de produtos
    }

    /**
     * Exibe o formulário para adicionar um novo produto.
     * 
     * @return Nome da view (adicionar-produto)
     */
    @GetMapping("/produtos/adicionar")
    public String mostrarFormularioAdicionarProduto() {
        return "adicionar-produto"; // Retorna a página para adicionar produto
    }

    /**
     * Processa a adição de um novo produto.
     * 
     * @param nome Nome do produto
     * @param preco Preço do produto
     * @param redirectAttributes Mensagens a serem exibidas após o redirecionamento
     * @return Redireciona para a lista de produtos com a mensagem de sucesso
     */
    @PostMapping("/produtos")
    public String adicionarProduto(@RequestParam String nome, @RequestParam double preco,
            RedirectAttributes redirectAttributes) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(preco);

        produtoService.salvarProduto(produto); // Salva o produto no banco de dados

        // Adiciona a mensagem de sucesso ao redirecionar
        redirectAttributes.addAttribute("sucesso", "Produto adicionado com sucesso!");
        return "redirect:/produtos"; // Redireciona para a página de produtos
    }

    /**
     * Exibe o formulário para editar um produto existente.
     * 
     * @param id Identificador do produto a ser editado
     * @param model Modelo para passar o produto a ser editado para a view
     * @return Nome da view (editar-produto)
     */
    @GetMapping("/produtos/editar")
    public String mostrarFormularioEditarProduto(@RequestParam("id") Long id, Model model) {
        Produto produto = produtoService.obterProdutos(id); // Obtém o produto a ser editado
        model.addAttribute("produto", produto);
        return "editar-produto"; // Retorna a página de edição de produto
    }

    /**
     * Processa a edição de um produto existente.
     * 
     * @param id Identificador do produto a ser editado
     * @param nome Novo nome do produto
     * @param preco Novo preço do produto
     * @param redirectAttributes Mensagens a serem exibidas após o redirecionamento
     * @return Redireciona para a lista de produtos com a mensagem de sucesso
     */
    @PostMapping("/produtos/editar")
    public String editarProduto(@RequestParam("id") Long id, @RequestParam("nome") String nome,
            @RequestParam("preco") double preco, RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.obterProdutos(id); // Obtém o produto a ser editado
        produto.setNome(nome);
        produto.setPreco(preco);

        produtoService.salvarProduto(produto); // Atualiza o produto no banco de dados

        // Adiciona a mensagem de sucesso ao redirecionar
        redirectAttributes.addAttribute("sucesso", "Produto atualizado com sucesso!");
        return "redirect:/produtos"; // Redireciona para a página de produtos
    }

    /**
     * Processa a exclusão de um produto.
     * 
     * @param id Identificador do produto a ser excluído
     * @param redirectAttributes Mensagens a serem exibidas após o redirecionamento
     * @return Redireciona para a lista de produtos com a mensagem de sucesso
     */
    @PostMapping("/produtos/deletar")
    public String deletarProduto(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        produtoService.excluirProduto(id); // Exclui o produto do banco de dados

        // Adiciona a mensagem de sucesso ao redirecionar
        redirectAttributes.addAttribute("sucesso", "Produto deletado com sucesso!");
        return "redirect:/produtos"; // Redireciona para a página de produtos
    }
}
