package com.example.folhaPagamento.service;

import java.util.List;

import com.example.folhaPagamento.model.Colaborador;
public class Relatorio {
    
    //Gera o total da folha de pagamento:
    public double calcularTotalFolha(List<Colaborador> colaboradores) {
        double total = 0;
        for (Colaborador colaborador : colaboradores) {
            total += colaborador.calcularSalarioFinal();
        }
        return total;
    }

    //Gera a folha de pagamento detalhada:
    public String relatorioDetalhado(List<Colaborador> colaboradores) {
        StringBuilder relatorio = new StringBuilder();
            relatorio.append("FOLHA DE PAGAMENTO DETALHADA:\n");
                relatorio.append("--------------------------------\n");
                    for (Colaborador colaborador : colaboradores) {
                        relatorio.append ("Colaborador ").append(colaborador.getTipoColaborador()).append (":").append("\n");
                        relatorio.append(colaborador.toString()).append("\n");
                        relatorio.append("--------------------------------\n");
                        
                    }
        return relatorio.toString();
    }

}
