package repository;

import model.Mesa;

import java.util.List;
import java.util.Optional;

public class MesaRepository {
    private RepositorioGenerico<Mesa> repo;

    public MesaRepository(String arquivo) {
        this.repo = new RepositorioGenerico<>(arquivo);
    }

    public List<Mesa> carregarTodos() { return repo.carregar(); }
    public void salvarTodos(List<Mesa> mesas) { repo.salvar(mesas); }

    public Optional<Mesa> buscarPorNumero(List<Mesa> mesas, int numero) {
        return mesas.stream().filter(m -> m.getNumero() == numero).findFirst();
    }
}
