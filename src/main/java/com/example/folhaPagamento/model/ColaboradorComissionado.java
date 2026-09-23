package com.example.folhaPagamento.model;

public class ColaboradorComissionado extends Colaborador {
    private double valorVendas;
    private double percentualComissao;

    public ColaboradorComissionado(String nome, int matricula, double salario, double valorVendas, double percentualComissao) {
        super(nome, matricula, salario);
        if (valorVendas < 0) {
            throw new IllegalArgumentException("Valor de vendas invalido! Seu valor de vendas não pode ser negativo.");
        }

        if (percentualComissao < 0) {
            throw new IllegalArgumentException("Percentual de comissão inválido! Seu percentual de vendas não pode ser negativo.");
        }

        this.valorVendas = valorVendas;
        this.percentualComissao = percentualComissao;
    }

    public double getValorVendas() {
        return valorVendas;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public double getComissao() {
        return valorVendas * percentualComissao;
    }

    public void setValorVendas(double valorVendas) {
        if (valorVendas < 0) {
            throw new IllegalArgumentException("Valor de vendas invalido! Seu valor de vendas não pode ser negativo.");
        }
        this.valorVendas = valorVendas;
    }

    @Override
    public double calcularSalarioFinal() {
        double comissao = (valorVendas * percentualComissao);
        return getSalario() + comissao;
    }

    @Override
    public String getTipoColaborador() {
        return "Comissionado";
    }

    @Override 
    public String toString() {
        return "Nome: " + getNome() + "\n" +
                "Matricula: " + getMatricula() + "\n" +
                "Tipo de Colaborador: " + getTipoColaborador() + "\n" +
                "Valor de Vendas: R$ " + String.format("%.2f", valorVendas) + "\n" +
                "Percentual de Comissão: " + String.format("%.2f", percentualComissao * 100) + "%" + "\n" +
                "Comissão: R$ " + String.format("%.2f", getComissao()) + "\n" +
                "Salário Final: R$ " + String.format("%.2f", calcularSalarioFinal()) + "\n";
    }
}
