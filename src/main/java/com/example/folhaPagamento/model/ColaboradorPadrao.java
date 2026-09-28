package com.example.folhaPagamento.model;

//Cria uma colaborador padrão que recebe apenas salário base sem adicionais
public class ColaboradorPadrao extends Colaborador {

    public ColaboradorPadrao(String nome, int matricula, double salario) {
        super(nome, //Nome completo do colaborador
        matricula, //Identicador do colaborador
        salario); //Remuneração do colaborador (Algo que não pode ser negativo)
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
