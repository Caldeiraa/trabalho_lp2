package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório genérico simples utilizando serialização.
 * Salva/Carrega listas de objetos em arquivo.
 */
public class RepositorioGenerico<T> {
    private String arquivo;

    public RepositorioGenerico(String arquivo) {
        this.arquivo = arquivo;
    }

    @SuppressWarnings("unchecked")
    public List<T> carregar() {
        File f = new File(arquivo);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void salvar(List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
