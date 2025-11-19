package model;

import java.io.Serializable;
import java.util.Objects;

public class ItemCardapio implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nome;
    private double preco;
    private Categoria categoria;

    public ItemCardapio(String codigo, String nome, double preco, Categoria categoria) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("Código inválido");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome inválido");
        if (preco < 0) throw new IllegalArgumentException("Preço não pode ser negativo");
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public Categoria getCategoria() { return categoria; }

    public void setNome(String nome) { 
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome inválido");
        this.nome = nome; 
    }
    public void setPreco(double preco) { 
        if (preco < 0) throw new IllegalArgumentException("Preço negativo");
        this.preco = preco; 
    }

    @Override
    public String toString() {
        return String.format("%s - %s (R$ %.2f) [%s]", codigo, nome, preco, categoria);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemCardapio)) return false;
        ItemCardapio item = (ItemCardapio) o;
        return codigo.equals(item.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
