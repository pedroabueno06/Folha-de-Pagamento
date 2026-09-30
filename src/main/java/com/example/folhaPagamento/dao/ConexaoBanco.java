package com.example.folhaPagamento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {
    private static final String URL = "jdbc:sqlite:folhaPagamento.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    
    }

    public static void criarTabelas() throws SQLException {
        String sqlColaboradores = "CREATE TABLE IF NOT EXISTS colaboradores (" +
            "MATRICULA INTEGER PRIMARY KEY, "+
            "NOME TEXT NOT NULL, "+
            "TIPO TEXT NOT NULL, "+
            "SALARIO REAL NOT NULL)";

            String sqlComissionados = "CREATE TABLE IF NOT EXISTS comissionados (" +
            "MATRICULA INTEGER PRIMARY KEY, " +
            "VALOR_VENDAS REAL NOT NULL, " +
            "PERCENTUAL_COMISSAO REAL NOT NULL, " +
            "FOREIGN KEY (matricula) REFERENCES colaboradores(matricula))";

            String sqlProducao = "CREATE TABLE IF NOT EXISTS producao (" +
            "MATRICULA INTEGER PRIMARY KEY, "+
            "QUANTIDADE_PRODUZIDA REAL NOT NULL, "+
            "VALOR_UNIDADE REAL NOT NULL, "+
            "FOREIGN KEY (matricula) REFERENCES colaboradores(matricula))";

        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
                
            statement.execute(sqlColaboradores);
            statement.execute(sqlComissionados);
            statement.execute(sqlProducao);
        }
    }
}
