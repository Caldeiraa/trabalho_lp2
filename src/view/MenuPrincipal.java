package view;

import controller.ItemController;
import controller.MesaController;
import controller.PedidoController;
import model.Categoria;
import model.Garcom;
import model.ItemCardapio;
import model.Mesa;
import model.Pedido;
import repository.ItemRepository;
import repository.MesaRepository;
import repository.PedidoRepository;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private MesaController mesaController;
    private ItemController itemController;
    private PedidoController pedidoController;
    private Scanner scanner;

    public MenuPrincipal() {
        // arquivos simples em /data
        MesaRepository mesaRepo = new MesaRepository("data/mesas.dat");
        ItemRepository itemRepo = new ItemRepository("data/itens.dat");
        PedidoRepository pedidoRepo = new PedidoRepository("data/pedidos.dat");

        this.mesaController = new MesaController(mesaRepo);
        this.itemController = new ItemController(itemRepo);
        this.pedidoController = new PedidoController(pedidoRepo);
        this.scanner = new Scanner(System.in);

        // cria 10 mesas por padrão se não existirem
        mesaController.criarMesas(10);
    }

    public void executar() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Mesas");
            System.out.println("2. Cardápio");
            System.out.println("3. Pedidos abertos");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            String op = scanner.nextLine();
            switch (op) {
                case "1": menuMesas(); break;
                case "2": menuCardapio(); break;
                case "3": listarPedidosAbertos(); break;
                case "0": sair(); return;
                default: System.out.println("Opção inválida"); break;
            }
        }
    }

    private void menuMesas() {
        System.out.println("\n--- Mesas ---");
        for (Mesa m : mesaController.listarMesas()) {
            System.out.println(m);
        }
        System.out.println("Digite número da mesa para abrir pedido (ou 0 para voltar): ");
        int num = Integer.parseInt(scanner.nextLine());
        if (num == 0) return;

        Mesa mesa = mesaController.getMesa(num).orElse(null);
        if (mesa == null) {
            System.out.println("Mesa inexistente");
            return;
        }
        if (mesa.getEstado().name().equals("OCUPADA")) {
            System.out.println("Mesa ocupada. Só é permitido abrir se estiver livre.");
            return;
        }

        // para demo simples, cria garçom fixo
        Garcom g = new Garcom("g1", "Garcom 1");

        // abre pedido
        Pedido pedido = pedidoController.abrirPedido(num, g);
        mesa.ocupar();
        mesaController.salvar();
        pedidoController.salvar();
        System.out.println("Pedido aberto: " + pedido.getId());
        gerenciarPedido(pedido);
    }

    private void gerenciarPedido(Pedido pedido) {
        while (true) {
            System.out.println("\n--- Pedido: " + pedido.getId() + " ---");
            System.out.println("Itens:");
            pedido.getItens().forEach(System.out::println);
            System.out.printf("Total: R$ %.2f\n", pedido.calcularTotal());
            System.out.println("1. Adicionar item");
            System.out.println("2. Remover item");
            System.out.println("3. Finalizar pedido");
            System.out.println("0. Voltar");
            String op = scanner.nextLine();
            switch (op) {
                case "1":
                    System.out.println("Código do item: ");
                    String codigo = scanner.nextLine();
                    ItemCardapio item = itemController.buscarPorCodigo(codigo).orElse(null);
                    if (item == null) {
                        System.out.println("Item não encontrado");
                        break;
                    }
                    System.out.println("Quantidade: ");
                    int q = Integer.parseInt(scanner.nextLine());
                    try {
                        pedido.adicionarItem(item, q);
                        pedidoController.salvar();
                        System.out.println("Item adicionado");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("Código do item a remover: ");
                    String codRem = scanner.nextLine();
                    pedido.removerItem(codRem);
                    pedidoController.salvar();
                    System.out.println("Item removido se existia");
                    break;
                case "3":
                    try {
                        pedido.finalizar();
                        // liberar mesa
                        mesaController.getMesa(pedido.getMesaNumero()).ifPresent(m -> m.liberar());
                        mesaController.salvar();
                        pedidoController.salvar();
                        System.out.println("Pedido finalizado. Total: R$ " + pedido.calcularTotal());
                        return;
                    } catch (Exception e) {
                        System.out.println("Erro ao finalizar: " + e.getMessage());
                    }
                    break;
                case "0": return;
                default: System.out.println("Opção inválida"); break;
            }
        }
    }

    private void menuCardapio() {
        while (true) {
            System.out.println("\n--- Cardápio ---");
            List<ItemCardapio> itens = itemController.listarItens();
            if (itens.isEmpty()) System.out.println("(vazio)");
            else itens.forEach(i -> System.out.println(i));
            System.out.println("1. Cadastrar item");
            System.out.println("0. Voltar");
            String op = scanner.nextLine();
            if ("1".equals(op)) {
                System.out.println("Código: ");
                String codigo = scanner.nextLine();
                System.out.println("Nome: ");
                String nome = scanner.nextLine();
                System.out.println("Preço: ");
                double preco = Double.parseDouble(scanner.nextLine());
                System.out.println("Categoria (PRATO, BEBIDA, SOBREMESA, ACOMPANHAMENTO): ");
                Categoria cat = Categoria.valueOf(scanner.nextLine().toUpperCase());
                try {
                    itemController.cadastrarItem(codigo, nome, preco, cat);
                    System.out.println("Item cadastrado");
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else if ("0".equals(op)) {
                return;
            } else {
                System.out.println("Opção inválida");
            }
        }
    }

    private void listarPedidosAbertos() {
        System.out.println("\n--- Pedidos Abertos ---");
        var abertos = pedidoController.listarAbertos();
        if (abertos.isEmpty()) System.out.println("(nenhum)");
        else abertos.forEach(System.out::println);
    }

    private void sair() {
        System.out.println("Saindo...");
        // salva tudo
        mesaController.salvar();
        itemController.salvar();
        pedidoController.salvar();
    }

    public static void main(String[] args) {
        new MenuPrincipal().executar();
    }
}
