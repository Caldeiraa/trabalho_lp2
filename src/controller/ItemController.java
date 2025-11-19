package controller;

import model.Categoria;
import model.ItemCardapio;
import repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemController {
    private List<ItemCardapio> itens;
    private ItemRepository repo;

    public ItemController(ItemRepository repo) {
        this.repo = repo;
        this.itens = new ArrayList<>(repo.carregarTodos());
    }

    public void cadastrarItem(String codigo, String nome, double preco, Categoria categoria) {
        if (repo.buscarPorCodigo(itens, codigo).isPresent()) {
            throw new IllegalArgumentException("Código já existe");
        }
        ItemCardapio item = new ItemCardapio(codigo, nome, preco, categoria);
        itens.add(item);
        repo.salvarTodos(itens);
    }

    public List<ItemCardapio> listarItens() { return itens; }

    public Optional<ItemCardapio> buscarPorCodigo(String codigo) {
        return repo.buscarPorCodigo(itens, codigo);
    }

    public void salvar() { repo.salvarTodos(itens); }
}
