package com.example.folhaPagamento.repositorio;

import java.util.ArrayList;
import java.util.List;

import com.example.folhaPagamento.model.Colaborador;
public class GerenciadorColaboradores {
    
    private List<Colaborador> colaboradores = new ArrayList<>();

    public boolean matriculaExistente(int matricula) {
        for (Colaborador colaborador : colaboradores) {
            if (colaborador.getMatricula() == matricula) {
                return true;
            }
        }
        return false;
    }

    public void adicionarColaborador (Colaborador colaborador) {
        if (matriculaExistente (colaborador.getMatricula())) {
            throw new IllegalArgumentException("Matrícula inválida! Este número de matrícula já está em uso. Por favor digite uma matrícula diferente.");
        }
        colaboradores.add(colaborador);
    }

    public List<Colaborador> getColaboradores() {
        return new ArrayList<>(colaboradores);
    }

    public boolean removerColaborador(int matricula) {
        for (Colaborador colaborador : colaboradores) {
            if (colaborador.getMatricula() == matricula) {
                colaboradores.remove(colaborador);
                return true;
            }
        }
        return false;
    }

    public void atualizarColaborador(int antigaMatricula, Colaborador novoColaborador) {
        boolean removeu = removerColaborador(antigaMatricula);

        if(!removeu) {
            throw new IllegalArgumentException("Matrícula inválida! Não há nenhum colaborador com este número de matrícula.");
        }
        
        adicionarColaborador(novoColaborador);
    }
}
