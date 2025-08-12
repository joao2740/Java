package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3307/sistema_clientes";
    private static final String USER = "root";
    private static final String PASSWORD = "senac";

    /**
     * Retorna uma conexão com o banco de dados.
     * 
     * @return Connection ou null em caso de erro
     */
    public static Connection getConnection() {
        try {
            // Registra o driver (opcional no JDBC 4+, mas bom para compatibilidade)
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado.");
            return null;
        }
    }
}
