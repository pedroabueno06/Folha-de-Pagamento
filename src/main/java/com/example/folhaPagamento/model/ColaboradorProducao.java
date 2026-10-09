package com.example.folhaPagamento.model;
import java.text.NumberFormat;

public class ColaboradorProducao extends Colaborador {
    private int quantidadeProduzida;
    private double valorUnidade;

    public ColaboradorProducao (String nome, int matricula, double salario, int quantidadeProduzida, double valorUnidade) {
        super(nome, matricula, salario);

        if (quantidadeProduzida < 0) {
            throw new IllegalArgumentException("Quantidade produzida inválida! A mesma não pode ser negativa.");
        }

        if (valorUnidade < 0) {
            throw new IllegalArgumentException("Valor por unidade inválido! O mesmo não pode ser negativo.");
        }

        this.quantidadeProduzida = quantidadeProduzida;
        this.valorUnidade = valorUnidade;
    }

    public int getQuantidadeProduzida() {
        return quantidadeProduzida;
    }

    public double getValorUnidade() {
        return valorUnidade;
    }

    public double getProdutividade() {
        return quantidadeProduzida * valorUnidade;
    }

    public void setQuantidadeProduzida(int quantidadeProduzida) {
        if (quantidadeProduzida < 0) {
            throw new IllegalArgumentException("Quantidade produzida inválida! A mesma não pode ser negativa.");
        }
        this.quantidadeProduzida = quantidadeProduzida;
    }

    public void setValorUnidade(double valorUnidade) {
        if (valorUnidade < 0) {
            throw new IllegalArgumentException("Valor por unidade inválido! O mesmo não pode ser negativo.");
        }
        this.valorUnidade = valorUnidade;
    }

    @Override 
    public double calcularSalarioFinal() {
        double produtividade = (quantidadeProduzida * valorUnidade); //Fórmula para calcular o valor da produtividade do colaborador de produção
        return getSalario() + produtividade; //Fórmula pra calcular o salário do colaborador de produção
    }

    @Override
    public String getTipoColaborador() {
        return "Produção";
    }

    @Override
    public String toString() {
        return "Nome: " + getNome() + "\n" +
                "Matrícula: " + getMatricula() + "\n" +
                "Tipo de Colaborador: " + getTipoColaborador() + "\n" +
                "Salário Base: R$ " + NumberFormat.getNumberInstance().format(getSalario()) + "\n" +
                "Quantidade Produzida: " + NumberFormat.getNumberInstance().format(quantidadeProduzida) + "\n" +
                "Valor por Unidade: R$ " + NumberFormat.getNumberInstance().format(valorUnidade) + "\n" +
                "Produtividade: R$ " + NumberFormat.getNumberInstance().format(getProdutividade()) + "\n" +
                "Salário Final: R$ " + NumberFormat.getNumberInstance().format(calcularSalarioFinal()) + "\n";

    }

}
