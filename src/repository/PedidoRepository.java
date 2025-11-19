package repository;

import model.Pedido;

import java.util.List;
import java.util.Optional;

public class PedidoRepository {
    private RepositorioGenerico<Pedido> repo;

    public PedidoRepository(String arquivo) { this.repo = new RepositorioGenerico<>(arquivo); }

    public List<Pedido> carregarTodos() { return repo.carregar(); }
    public void salvarTodos(List<Pedido> pedidos) { repo.salvar(pedidos); }

    public Optional<Pedido> buscarPorId(List<Pedido> pedidos, String id) {
        return pedidos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}
