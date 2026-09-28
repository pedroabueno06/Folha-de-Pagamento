package com.example.folhaPagamento.repositorio;

import java.util.ArrayList;
import java.util.List;

import com.example.folhaPagamento.model.Colaborador;
public class GerenciadorColaboradores {
    
    //Adiciona o cadastro dos colaboradores dentro de um ArrayList
    private List<Colaborador> colaboradores = new ArrayList<>();

    
    public boolean nomeExistente (String nome) {
        for (Colaborador colaborador : colaboradores) {
            if (colaborador.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }

    //verifica se a matrícula do colaborador já foi castrada
    public boolean matriculaExistente(int matricula) {
        for (Colaborador colaborador : colaboradores) {
            if (colaborador.getMatricula() == matricula) {
                return true;
            }
        }
        return false;
    }

    //Para adicionar um novo colaborador é necessário que a sua matrícula não esteja castrada no sistema
    public void adicionarColaborador (Colaborador colaborador) {
        if (matriculaExistente (colaborador.getMatricula())) {
            throw new IllegalArgumentException("Matrícula Inválida! Digite um número de matrícula que não esteja em uso.");
        }
        colaboradores.add(colaborador);
    }

    public List<Colaborador> getColaboradores() {
        return new ArrayList<>(colaboradores);
    }

    //Um colaborador só é removido do sistema se a matricula do mesma estiver castrada
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

        //! quere dizer que se o colaborador não foi removido do sistema, o meso pode ter seu cadastro atualizado
        if(!removeu) {
            throw new IllegalArgumentException("Matrícula inválida! Não há nenhum colaborador com este número de matrícula.");
        }
        
        adicionarColaborador(novoColaborador);
    }
}
