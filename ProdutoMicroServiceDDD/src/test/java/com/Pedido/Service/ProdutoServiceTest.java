package com.Pedido.Service;

import com.Pedido.Model.Produto;
import com.Pedido.Repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    public ProdutoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveProduto_Sucesso() {
        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoRepository.save(produto)).thenReturn(produto);

        produtoService.saveProduto(produto);

        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    public void getAllProdutos_Sucesso() {
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        produtoService.getAllProdutos();

        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    public void getProdutoById_Sucesso() {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        produtoService.getProdutoById(1L);

        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void atualizarQuantidade_Sucesso() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setQuantidade(10);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        produtoService.atualizarQuantidade(1L, 20);

        verify(produtoRepository, times(1)).save(produto);
        assert(produto.getQuantidade() == 20);
    }

    @Test
    public void atualizarQuantidade_ProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        produtoService.atualizarQuantidade(1L, 20);

        verify(produtoRepository, never()).save(any(Produto.class));
    }
}
