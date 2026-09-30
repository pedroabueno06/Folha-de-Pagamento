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
        String sqlColaboradores = "create table if not exists colaboradores (" +
            "matricula integer primary key, "+
            "nome text not null, "+
            "tipo text not null, "+
            "salario real not null)";

            String sqlComissionados = "create table if not exists comissionados (" +
            "matricula integer primary key, " +
            "valor_vendas real not null, " +
            "percentual_comissao real not null, " +
            "foreign key (matricula) references colaboradores(matricula))";

            String sqlProducao = "create table if not exists producao (" +
            "matricula integer primary key, "+
            "quantidade_produzida real not null, "+
            "valor_unidade real not null, "+
            "foreign key (matricula) references colaboradores(matricula))";

        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
                
            statement.execute(sqlColaboradores);
            statement.execute(sqlComissionados);
            statement.execute(sqlProducao);
        }
    }
}
