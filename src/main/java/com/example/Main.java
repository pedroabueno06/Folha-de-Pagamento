package com.example;

import com.example.folhaPagamento.repositorio.GerenciadorColaboradores;
import com.example.folhaPagamento.view.Cadastro;
public class Main {
    public static void main(String[] args) {
        GerenciadorColaboradores gerenciador = new GerenciadorColaboradores();
        Cadastro cadastro = new Cadastro(gerenciador);
        cadastro.iniciar();
    }
}