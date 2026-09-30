package com.example;

import java.sql.SQLException;

import com.example.folhaPagamento.dao.ConexaoBanco;
import com.example.folhaPagamento.repositorio.GerenciadorColaboradores;
import com.example.folhaPagamento.view.Cadastro;
public class Main {
    public static void main(String[] args) {
        //É feito try catch pois o método Conexao.criarTabelas() possui um em sua assinatura e com ele pode realizar uma exceção verificada
        try {
            ConexaoBanco.criarTabelas();
        } catch (SQLException e) {
            System.out.println("Erro ao tentar criar as tabelas do banco de dados: " + e.getMessage());
            return; //Encerra o programa
        }
        
        GerenciadorColaboradores gerenciador = new GerenciadorColaboradores();
        Cadastro cadastro = new Cadastro(gerenciador);
        cadastro.iniciar();
    }
}