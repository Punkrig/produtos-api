package com.example.produtosapi.service;

import com.example.produtosapi.dto.ProdutoDTO;
import com.example.produtosapi.exception.ResourceNotFoundException;
import com.example.produtosapi.model.Produto;
import com.example.produtosapi.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
    }

    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Produto salvar(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDTO, produto);
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, ProdutoDTO produtoDTO) {
        Produto produtoExistente = buscarPorId(id);

        BeanUtils.copyProperties(produtoDTO, produtoExistente);
        produtoExistente.setId(id); // Garantir que o ID permaneça o mesmo

        return produtoRepository.save(produtoExistente);
    }

    public void excluir(Long id) {
        buscarPorId(id); // Verificar se o produto existe
        produtoRepository.deleteById(id);
    }
}
