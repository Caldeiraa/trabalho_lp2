package repository;

import model.ItemCardapio;

import java.util.List;
import java.util.Optional;

public class ItemRepository {
    private RepositorioGenerico<ItemCardapio> repo;

    public ItemRepository(String arquivo) { this.repo = new RepositorioGenerico<>(arquivo); }

    public List<ItemCardapio> carregarTodos() { return repo.carregar(); }
    public void salvarTodos(List<ItemCardapio> itens) { repo.salvar(itens); }

    public Optional<ItemCardapio> buscarPorCodigo(List<ItemCardapio> itens, String codigo) {
        return itens.stream().filter(i -> i.getCodigo().equals(codigo)).findFirst();
    }
}
