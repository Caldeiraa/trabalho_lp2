package model;

/**
 * Garçom extende Pessoa — exemplo de herança (requisito do trabalho).
 */
public class Garcom extends Pessoa {
    private static final long serialVersionUID = 1L;

    public Garcom(String id, String nome) {
        super(id, nome);
    }

    // Podemos incluir métodos específicos do garçom no futuro
}
