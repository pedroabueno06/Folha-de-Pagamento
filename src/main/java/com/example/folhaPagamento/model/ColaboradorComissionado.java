package com.example.folhaPagamento.model;
import java.text.NumberFormat;

public class ColaboradorComissionado extends Colaborador {
    private double valorVendas;
    private double percentualComissao;

    public ColaboradorComissionado(String nome, int matricula, double salario, double valorVendas, double percentualComissao) {
        super(nome, matricula, salario);
        if (valorVendas < 0) {
            throw new IllegalArgumentException("Valor de vendas inválido! Seu valor de vendas não pode ser negativo.");
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
        return (valorVendas * percentualComissao) /100; //Fórmula para calcula a comissão do colaborador comissionado
    }

    public void setValorVendas(double valorVendas) {
        if (valorVendas < 0) {
            throw new IllegalArgumentException("Valor de vendas inválido! Seu valor de vendas não pode ser negativo.");
        }
        this.valorVendas = valorVendas;
    }

    @Override
    public double calcularSalarioFinal() {
        double comissao = (valorVendas * percentualComissao) /100;
        return getSalario() + comissao; //Fórmula para calcular o salário final do colaborador comissionado
    }

    @Override
    public String getTipoColaborador() {
        return "Comissionado";
    }

    @Override 
    public String toString() {
        return "Nome: " + getNome() + "\n" +
                "Matrícula: " + getMatricula() + "\n" +
                "Tipo de Colaborador: " + getTipoColaborador() + "\n" +
                "Salário Base: R$ " + NumberFormat.getNumberInstance().format(getSalario()) + "\n" +
                "Valor de Vendas: R$ " + NumberFormat.getNumberInstance().format(valorVendas) + "\n" +
                "Percentual de Comissão: " + NumberFormat.getNumberInstance().format(percentualComissao) + "%" + "\n" +
                "Comissão: R$ " + NumberFormat.getNumberInstance().format(getComissao()) + "\n" + 
                "Salário Final: R$ " + NumberFormat.getNumberInstance().format(calcularSalarioFinal()) + "\n";
    }
}
