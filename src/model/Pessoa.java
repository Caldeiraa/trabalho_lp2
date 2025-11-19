package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe base para pessoas (ex: Garçom) - demonstra herança.
 */
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String nome;

    public Pessoa(String id, String nome) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID inválido");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome inválido");
        this.id = id;
        this.nome = nome;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome inválido");
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("%s (id=%s)", nome, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa p = (Pessoa) o;
        return id.equals(p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
