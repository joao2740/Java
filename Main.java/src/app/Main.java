package app;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClienteDAO dao = new ClienteDAO();

        while (true) {
            exibirMenu();
            int opcao = lerInteiro(sc, "Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarCliente(sc, dao);
                case 2 -> listarClientes(dao);
                case 3 -> atualizarCliente(sc, dao);
                case 4 -> deletarCliente(sc, dao);
                case 0 -> {
                    System.out.println("Saindo...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Listar Clientes");
        System.out.println("3 - Atualizar Cliente");
        System.out.println("4 - Deletar Cliente");
        System.out.println("0 - Sair");
    }

    private static int lerInteiro(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        while (!sc.hasNextInt()) {
            System.out.println("Entrada inválida! Digite um número.");
            sc.next();
            System.out.print(mensagem);
        }
        int valor = sc.nextInt();
        sc.nextLine(); // limpar buffer
        return valor;
    }

    private static String lerTexto(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine();
    }

    private static void cadastrarCliente(Scanner sc, ClienteDAO dao) {
        String nome = lerTexto(sc, "Nome: ");
        String email = lerTexto(sc, "Email: ");
        dao.inserir(new Cliente(nome, email));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void listarClientes(ClienteDAO dao) {
        List<Cliente> clientes = dao.listarClientes();
        System.out.println("\n--- Lista de Clientes ---");
        for (Cliente c : clientes) {
            System.out.println(c.getId() + " - " + c.getNome() + " (" + c.getEmail() + ")");
        }
    }

    private static void atualizarCliente(Scanner sc, ClienteDAO dao) {
        int id = lerInteiro(sc, "ID do cliente para atualizar: ");
        String novoNome = lerTexto(sc, "Novo nome: ");
        String novoEmail = lerTexto(sc, "Novo email: ");
        dao.atualizar(new Cliente(id, novoNome, novoEmail));
        System.out.println("Cliente atualizado com sucesso!");
    }

    private static void deletarCliente(Scanner sc, ClienteDAO dao) {
        int id = lerInteiro(sc, "ID do cliente para deletar: ");
        dao.deletar(id);
        System.out.println("Cliente deletado com sucesso!");
    }
}
