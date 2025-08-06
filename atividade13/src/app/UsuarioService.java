package app;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private List<String> usuarios;

    // Construtor: inicializa a lista de usuários
    public UsuarioService() {
        usuarios = new ArrayList<>();
    }

    // Método para cadastrar usuário com validação
    public void cadastrarUsuario(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome inválido! Não foi possível cadastrar.");
            return;
        }

        usuarios.add(nome.trim());
        System.out.println("Usuário '" + nome.trim() + "' cadastrado com sucesso!");
    }

    // Método para listar usuários
    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        System.out.println("Lista de usuários:");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i));
        }
    }
}
