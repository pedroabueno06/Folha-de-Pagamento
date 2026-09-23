package com.example.folhaPagamento.model;

public abstract class Colaborador {
    private String nome;
    private int matricula;
    private double salario;

    public Colaborador (String nome, int matricula, double salario) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome Inválido! Você não pode deixar seu nome em branco, além de ter quer informar seu nome completo.");
        }

        if(salario < 0) {
            throw new IllegalArgumentException("Salário inválido! Seu salário não pode ser negativo.");
        }
        
        this.nome = nome;
        this.matricula = matricula;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public int getMatricula() { //Não foi criado um setMatrícula pois após o colaborador inseri-la, a mesma não pode ser mudada.
        return matricula;
    }

    public double getSalario() {
        return salario;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome Inválido! Você não pode deixar seu nome em branco, além de ter quer informar seu nome completo.");
        }
        
        this.nome = nome;
    }

    public abstract String getTipoColaborador(); //Este método é abstrato pois cada colaborador terá seu próprio tipo e será obrigada a implementar este método.

    public abstract double calcularSalarioFinal(); //Este método é abstrato pois cada colaborador terá sua própria forma de calcular seu salário.

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Matricula: " + matricula  + "\n" +
                "Tipo de Colaborador: " + getTipoColaborador() + "\n" +
                "Salario: R$ " + String.format("%.2f", calcularSalarioFinal()) + "\n";

    }
}
