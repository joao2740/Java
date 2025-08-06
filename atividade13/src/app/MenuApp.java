package app;

import java.util.Scanner;

public class MenuApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsuarioService usuarioService = new UsuarioService();

        int opcao;

        do {
            // Exibe o menu
            System.out.println("===== MENU =====");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Listar usuários");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            // Lê a opção do usuário
            while (!scanner.hasNextInt()) {
                System.out.print("Opção inválida. Digite um número: ");
                scanner.next(); // limpa entrada inválida
            }

            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            // Executa a ação com base na opção
            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do usuário: ");
                    String nome = scanner.nextLine();
                    usuarioService.cadastrarUsuario(nome);
                    break;

                case 2:
                    usuarioService.listarUsuarios();
                    break;

                case 3:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

            System.out.println(); // Linha em branco para separar iterações

        } while (opcao != 3);

        scanner.close();
    }
}
