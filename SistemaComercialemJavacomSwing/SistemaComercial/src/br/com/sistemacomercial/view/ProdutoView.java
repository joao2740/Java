package br.com.sistemacomercial.view;

import br.com.sistemacomercial.controller.ProdutoController;
import br.com.sistemacomercial.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProdutoView extends JFrame {
    private final ProdutoController controller = new ProdutoController();

    private JTextField txtNome;
    private JTextField txtPreco;
    private JLabel lblIdAtual; // mostra "novo" ou o ID em edição

    private JTable tabela;
    private DefaultTableModel modelo;

    // Guarda o ID do produto em edição; -1 = criando novo
    private int idEmEdicao = -1;

    public ProdutoView() {
        setTitle("Cadastro de Produtos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 460);
        setLocationRelativeTo(null);

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        // Cabeçalho
        JLabel titulo = new JLabel("Produtos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(12,16,8,16));

        // Formulário
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(0,16,8,16));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        lblIdAtual = new JLabel("ID: novo");
        lblIdAtual.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel lblNome = new JLabel("Nome");
        txtNome = new JTextField();

        JLabel lblPreco = new JLabel("Preço (ex: 19.90)");
        txtPreco = new JTextField();

        JButton btnSalvar = estilizarBotaoPrimario("Salvar");
        JButton btnLimpar = estilizarBotaoSecundario("Limpar");

        // linhas/colunas
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1;
        form.add(lblIdAtual, gc);

        gc.gridx = 0; gc.gridy = 1; form.add(lblNome, gc);
        gc.gridx = 1; gc.gridy = 1; gc.weightx = 1.0; form.add(txtNome, gc);
        gc.weightx = 0;

        gc.gridx = 0; gc.gridy = 2; form.add(lblPreco, gc);
        gc.gridx = 1; gc.gridy = 2; gc.weightx = 1.0; form.add(txtPreco, gc);
        gc.weightx = 0;

        JPanel botoesForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botoesForm.add(btnSalvar);
        botoesForm.add(btnLimpar);

        gc.gridx = 1; gc.gridy = 3; gc.weightx = 0; form.add(botoesForm, gc);

        // Tabela
        modelo = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabela);

        // Botões de ação da lista
        JButton btnCarregar = estilizarBotaoSecundario("Editar (carregar)");
        JButton btnExcluir = estilizarBotaoPerigo("Excluir selecionado");
        JButton btnAtualizar = estilizarBotaoSecundario("Atualizar lista");

        JPanel acoesTabela = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        acoesTabela.add(btnAtualizar);
        acoesTabela.add(btnCarregar);
        acoesTabela.add(btnExcluir);

        // Layout principal
        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        add(form, BorderLayout.WEST);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBorder(BorderFactory.createEmptyBorder(0,0,8,16));
        centro.add(scroll, BorderLayout.CENTER);
        centro.add(acoesTabela, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);

        // Eventos
        btnSalvar.addActionListener(this::onSalvar);
        btnLimpar.addActionListener(e -> limparFormulario());
        btnAtualizar.addActionListener(e -> carregarTabela());
        btnCarregar.addActionListener(e -> carregarSelecionadoParaEdicao());
        btnExcluir.addActionListener(e -> excluirSelecionado());

        // Estado inicial
        carregarTabela();
    }

    private JButton estilizarBotaoPrimario(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(new Color(33,150,243));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(140, 34));
        return b;
    }

    private JButton estilizarBotaoSecundario(String texto) {
        JButton b = new JButton(texto);
        b.setPreferredSize(new Dimension(160, 34));
        return b;
    }

    private JButton estilizarBotaoPerigo(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(new Color(244, 67, 54));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(160, 34));
        return b;
    }

    private void onSalvar(ActionEvent e) {
        String nome = txtNome.getText().trim();
        String precoStr = txtPreco.getText().trim();

        if (nome.isEmpty() || precoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha nome e preço.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double preco;
        try {
            preco = Double.parseDouble(precoStr.replace(",", ".")); // aceita vírgula
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Use formato 99.99", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (idEmEdicao == -1) {
            // novo
            Produto p = controller.adicionar(nome, preco);
            JOptionPane.showMessageDialog(this, "Produto cadastrado (ID " + p.getId() + ").");
        } else {
            // atualização
            boolean ok = controller.atualizar(idEmEdicao, nome, preco);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Produto atualizado (ID " + idEmEdicao + ").");
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        limparFormulario();
        carregarTabela();
    }

    private void carregarTabela() {
        modelo.setRowCount(0);
        for (Produto p : controller.listar()) {
            modelo.addRow(new Object[]{ p.getId(), p.getNome(), String.format("%.2f", p.getPreco()) });
        }
    }

    private void carregarSelecionadoParaEdicao() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tabela.getValueAt(linha, 0);
        Produto p = controller.buscarPorId(id);
        if (p != null) {
            idEmEdicao = p.getId();
            lblIdAtual.setText("ID: " + idEmEdicao);
            txtNome.setText(p.getNome());
            txtPreco.setText(String.valueOf(p.getPreco()));
        }
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tabela.getValueAt(linha, 0);
        int confirma = JOptionPane.showConfirmDialog(this,
                "Confirma excluir o produto ID " + id + "?", "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            controller.remover(id);
            if (idEmEdicao == id) limparFormulario();
            carregarTabela();
        }
    }

    private void limparFormulario() {
        idEmEdicao = -1;
        lblIdAtual.setText("ID: novo");
        txtNome.setText("");
        txtPreco.setText("");
        tabela.clearSelection();
    }
}
