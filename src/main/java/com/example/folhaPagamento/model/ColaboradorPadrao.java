package com.example.folhaPagamento.model;

public class ColaboradorPadrao extends Colaborador {

    public ColaboradorPadrao(String nome, int matricula, double salario) {
        super(nome, matricula, salario);
    }

    @Override
    public double calcularSalarioFinal() {
        return getSalario();
    }

    @Override
    public String getTipoColaborador() {
        return "Padrão";
    }
}
