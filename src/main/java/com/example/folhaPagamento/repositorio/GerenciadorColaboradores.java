package com.example.folhaPagamento.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.folhaPagamento.dao.ConexaoBanco;
import com.example.folhaPagamento.model.Colaborador;
import com.example.folhaPagamento.model.ColaboradorComissionado;
import com.example.folhaPagamento.model.ColaboradorProducao;
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

    //Para adicionar um novo colaborador é necessário que a sua matrícula não esteja castrada no sistema
    public void adicionarColaborador (Colaborador colaborador) throws SQLException {
        String sqlBase = "INSERT INTO colaboradores (matricula, nome, tipo, salario) VALUES (?, ?, ?, ?)"; //Foi colocado ?, ou seja, com posições em branco dentro de values por questões de maior segurança e fazer com que os dados sejam enviados ao banco no formato correto
        String sqlComisisonados = "INSERT INTO comissionados (matricula, valor_vendas, percentual_comissao) VALUES (?, ?, ?)";
        String sqlProducao = "INSERT INTO producao (matricula, quantidade_produzida, valor_unidade) VALUES (?, ?, ?)";
        
Connection connetion = ConexaoBanco.getConnection();
            try {
                connetion.setAutoCommit(false);

                try (PreparedStatement statementBase = connetion.prepareStatement(sqlBase)) {
                    statementBase.setInt(1, colaborador.getMatricula());
                    statementBase.setString(2, colaborador.getNome());
                    statementBase.setString(3, colaborador.getTipoColaborador());
                    statementBase.setDouble(4, colaborador.getSalario());
                    statementBase.executeUpdate();

                    //É feito instanceof pois ao cadastrar um colaborador o programa verificara se o mesmo é do tipo comissionado ou produção para ser colocado em sua respectiva tabela
                    if (colaborador instanceof ColaboradorComissionado) {
                        ColaboradorComissionado colaboradorComissionado = (ColaboradorComissionado) colaborador;

                        try (PreparedStatement statementComissionados = connetion.prepareStatement(sqlComisisonados)) {
                            statementComissionados.setInt(1, colaboradorComissionado.getMatricula());
                            statementComissionados.setDouble(2, colaboradorComissionado.getValorVendas());
                            statementComissionados.setDouble(3, colaboradorComissionado.getPercentualComissao());
                            statementComissionados.executeUpdate();
                        }
                    }

                    //É feito instanceof pois ao cadastrar um colaborador o programa verificara se o mesmo é do tipo comissionado ou produção para ser colocado em sua respectiva tabela
                    if (colaborador instanceof ColaboradorProducao) {
                        ColaboradorProducao colaboradorProducao = (ColaboradorProducao) colaborador;

                        try (PreparedStatement statementProducao = connetion.prepareStatement(sqlProducao)) {
                            statementProducao.setInt(1, colaboradorProducao.getMatricula());
                            statementProducao.setDouble(2, colaboradorProducao.getQuantidadeProduzida());
                            statementProducao.setDouble(3, colaboradorProducao.getValorUnidade());
                            statementProducao.executeUpdate();
                        }
                    }
                }

                connetion.commit();

            } catch (SQLException e) {
                connetion.rollback();
                    throw e;
            } finally {
                connetion.close();
            }
        
        
        
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

    public void atualizarColaborador(int antigaMatricula, Colaborador novoColaborador) throws SQLException {
        boolean removeu = removerColaborador(antigaMatricula);

        //! quere dizer que se o colaborador não foi removido do sistema, o meso pode ter seu cadastro atualizado
        if(!removeu) {
            throw new IllegalArgumentException("Matrícula inválida! Não há nenhum colaborador com este número de matrícula.");
        }
        
        adicionarColaborador(novoColaborador);
    }
}
