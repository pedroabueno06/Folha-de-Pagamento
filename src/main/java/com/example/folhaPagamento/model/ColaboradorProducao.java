package com.example.folhaPagamento.model;

public class ColaboradorProducao extends Colaborador {
    private double quantidadeProduzida;
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

    public double getQuantidadeProduzida() {
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
        double produtividade = (quantidadeProduzida * valorUnidade);
        return getSalario() + produtividade;
    }

    @Override 
    public String getTipoColaborador() {
        return "Produção";
    }

    @Override 
    public String toString() {
        return "Nome: " + getNome() + "\n" +
                "Matricula: " + getMatricula() + "\n" + 
                "Tipo de Colaborador: " + getTipoColaborador() + "\n" +
                "Salário Base: R$ " + String.format("%.2f", getSalario()) + "\n" +
                "Quantidade Produzida: " + String.format("%.2f", quantidadeProduzida) + "\n" +
                "Valor por Unidade: R$ " + String.format("%.2f", valorUnidade) + "\n" +
                "Produtividade: R$ " + String.format("%.2f", getProdutividade()) + "\n" +
                "Salário Final: R$ " + String.format("%.2f", calcularSalarioFinal()) + "\n";

    }

}
