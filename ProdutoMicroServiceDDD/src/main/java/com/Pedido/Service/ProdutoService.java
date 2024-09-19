package com.Pedido.Service;

import com.Pedido.Model.Produto;
import com.Pedido.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto saveProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> getProdutoById(Long id) {
        return produtoRepository.findById(id);
    }
    public void atualizarQuantidade(Long id, Integer quantidade) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto != null) {
            produto.setQuantidade(quantidade);
            produtoRepository.save(produto);
        }

}
}