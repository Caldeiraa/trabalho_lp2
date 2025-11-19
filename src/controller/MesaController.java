package controller;

import model.Mesa;
import repository.MesaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MesaController {
    private List<Mesa> mesas;
    private MesaRepository mesaRepo;

    public MesaController(MesaRepository repo) {
        this.mesaRepo = repo;
        this.mesas = new ArrayList<>(repo.carregarTodos());
    }

    public void criarMesas(int quantidade) {
        for (int i = 1; i <= quantidade; i++) {
            // Create a *new*, effectively final variable for each iteration
            final int numeroMesa = i; 
            if (mesas.stream().noneMatch(m -> m.getNumero() == numeroMesa)) {
                mesas.add(new Mesa(numeroMesa));
            }
        }
        mesaRepo.salvarTodos(mesas);
    }


    public List<Mesa> listarMesas() { return mesas; }

    public Optional<Mesa> getMesa(int numero) {
        return mesas.stream().filter(m -> m.getNumero() == numero).findFirst();
    }

    public void salvar() { mesaRepo.salvarTodos(mesas); }
}
