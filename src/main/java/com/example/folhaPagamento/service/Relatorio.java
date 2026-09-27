package com.example.folhaPagamento.service;

import java.util.ArrayList;
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
        List<Colaborador> padrao =  new ArrayList<>();
        List<Colaborador> comissionado = new ArrayList<>();
        List<Colaborador> producao = new ArrayList<>();

        for (Colaborador colaborador : colaboradores) {
            if (colaborador.getTipoColaborador().equals("Padrão")) {
                padrao.add(colaborador);

            } else if (colaborador.getTipoColaborador().equals("Comissionado")) {
                comissionado.add(colaborador);

            } else {
                producao.add(colaborador);
            }
        }
        StringBuilder relatorio = new StringBuilder();
            relatorio.append("\n-----------------------------\n");
                relatorio.append("FOLHA DE PAGAMENTO DETALHADA\n");
                    relatorio.append("-----------------------------\n");

            relatorio.append("\n-------------------\n");
                relatorio.append("COLABORADOR PADRÃO\n");
                    relatorio.append("-------------------\n");
                        for (Colaborador colaborador : padrao) {
                            relatorio.append(colaborador.toString()).append("\n");
                                relatorio.append("-------------------------\n");
                            }
                    
                    relatorio.append("COLABORADOR COMISSIONADO\n");
                        relatorio.append("-------------------------\n");
                            for (Colaborador colaborador : comissionado) {
                                relatorio.append(colaborador.toString()).append("\n");
                                    relatorio.append("---------------------\n");

                            }

                    relatorio.append("COLABORADOR PRODUÇÃO\n");
                        relatorio.append("---------------------\n");
                            for (Colaborador colaborador : producao) {
                                relatorio.append(colaborador.toString()).append("\n");
                                    relatorio.append("---------------------------------------\ns");

                            }

        return relatorio.toString();
    }

}