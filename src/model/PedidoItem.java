package model;

import java.io.Serializable;
import java.util.Objects;

public class PedidoItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private ItemCardapio item;
    private int quantidade;

    public PedidoItem(ItemCardapio item, int quantidade) {
        if (item == null) throw new IllegalArgumentException("Item nulo");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser > 0");
        this.item = item;
        this.quantidade = quantidade;
    }

    public ItemCardapio getItem() { return item; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser > 0");
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return item.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s x%d = R$ %.2f", item.getNome(), quantidade, getSubtotal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PedidoItem)) return false;
        PedidoItem pi = (PedidoItem) o;
        return item.equals(pi.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
}
