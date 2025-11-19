package model;

import java.io.Serializable;
import java.util.Objects;

public class Mesa implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Estado { LIVRE, OCUPADA, AGUARDANDO_PAGAMENTO }

    private int numero;
    private Estado estado;

    public Mesa(int numero) {
        if (numero <= 0) throw new IllegalArgumentException("Número de mesa inválido");
        this.numero = numero;
        this.estado = Estado.LIVRE;
    }

    public int getNumero() { return numero; }
    public Estado getEstado() { return estado; }

    public void ocupar() {
        if (estado == Estado.OCUPADA) throw new IllegalStateException("Mesa já ocupada");
        estado = Estado.OCUPADA;
    }

    public void liberar() {
        estado = Estado.LIVRE;
    }

    public void aguardarPagamento() {
        estado = Estado.AGUARDANDO_PAGAMENTO;
    }

    @Override
    public String toString() {
        return String.format("Mesa %d [%s]", numero, estado);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mesa)) return false;
        Mesa m = (Mesa) o;
        return numero == m.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
