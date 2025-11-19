package controller;

import model.Garcom;
import model.ItemCardapio;
import model.Pedido;
import repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PedidoController {
    private List<Pedido> pedidos;
    private PedidoRepository repo;

    public PedidoController(PedidoRepository repo) {
        this.repo = repo;
        this.pedidos = new ArrayList<>(repo.carregarTodos());
    }

    public Pedido abrirPedido(int mesaNumero, Garcom garcom) {
        // verifica se já existe pedido aberto para a mesa
        for (Pedido p : pedidos) {
            if (p.getMesaNumero() == mesaNumero && p.getStatus() == Pedido.Status.ABERTO) {
                throw new IllegalStateException("Já existe um pedido aberto para essa mesa");
            }
        }
        String id = UUID.randomUUID().toString();
        Pedido pedido = new Pedido(id, mesaNumero, garcom);
        pedidos.add(pedido);
        repo.salvarTodos(pedidos);
        return pedido;
    }

    public Optional<Pedido> buscarPorId(String id) { return repo.buscarPorId(pedidos, id); }

    public List<Pedido> listarAbertos() {
        List<Pedido> res = new ArrayList<>();
        for (Pedido p : pedidos) if (p.getStatus() == Pedido.Status.ABERTO) res.add(p);
        return res;
    }

    public void salvar() { repo.salvarTodos(pedidos); }
}
