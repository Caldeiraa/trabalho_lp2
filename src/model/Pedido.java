package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Status { ABERTO, FINALIZADO }

    private String id;
    private int mesaNumero;
    private List<PedidoItem> itens;
    private LocalDateTime criadoEm;
    private Status status;
    private Garcom garcom;

    public Pedido(String id, int mesaNumero, Garcom garcom) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID inválido");
        if (mesaNumero <= 0) throw new IllegalArgumentException("Número de mesa inválido");
        this.id = id;
        this.mesaNumero = mesaNumero;
        this.itens = new ArrayList<>();
        this.criadoEm = LocalDateTime.now();
        this.status = Status.ABERTO;
        this.garcom = garcom;
    }

    public String getId() { return id; }
    public int getMesaNumero() { return mesaNumero; }
    public List<PedidoItem> getItens() { return itens; }
    public Status getStatus() { return status; }
    public Garcom getGarcom() { return garcom; }

    public void adicionarItem(ItemCardapio item, int quantidade) {
        if (status != Status.ABERTO) throw new IllegalStateException("Pedido não está aberto");
        PedidoItem novo = new PedidoItem(item, quantidade);
        // se o item já existe, soma quantidade
        int idx = itens.indexOf(novo);
        if (idx >= 0) {
            PedidoItem existente = itens.get(idx);
            existente.setQuantidade(existente.getQuantidade() + quantidade);
        } else {
            itens.add(novo);
        }
    }

    public void removerItem(String codigoItem) {
        itens.removeIf(pi -> pi.getItem().getCodigo().equals(codigoItem));
    }

    public double calcularTotal() {
        return itens.stream().mapToDouble(PedidoItem::getSubtotal).sum();
    }

    public void finalizar() {
        if (itens.isEmpty()) throw new IllegalStateException("Pedido vazio não pode ser finalizado");
        this.status = Status.FINALIZADO;
    }

    @Override
    public String toString() {
        return String.format("Pedido %s - Mesa %d - Status: %s - Total: R$ %.2f", id, mesaNumero, status, calcularTotal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido p = (Pedido) o;
        return id.equals(p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
