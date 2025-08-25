package br.com.sistemacomercial.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import br.com.sistemacomercial.view.TelaCadastroProduto; // importa a tela de cadastro

public class LoginView extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public LoginView() {
        setTitle("Login - Sistema Comercial");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Usuário
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Usuário:"), gbc);
        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        painel.add(txtUsuario, gbc);

        // Senha
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        txtSenha = new JPasswordField(15);
        painel.add(txtSenha, gbc);

        // Botão
        gbc.gridx = 1; gbc.gridy = 2;
        JButton btnLogin = new JButton("Entrar");
        painel.add(btnLogin, gbc);

        add(painel, BorderLayout.CENTER);

        // Ação do botão
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String senha = new String(txtSenha.getPassword());

                if(usuario.equals("admin") && senha.equals("123")) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                    dispose(); // fecha a tela de login
                    new TelaCadastroProduto().setVisible(true); // abre tela de produtos
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
