package com.example.folhaPagamento.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.folhaPagamento.dao.ConexaoBanco;
import com.example.folhaPagamento.model.Colaborador;
import com.example.folhaPagamento.model.ColaboradorComissionado;
import com.example.folhaPagamento.model.ColaboradorPadrao;
import com.example.folhaPagamento.model.ColaboradorProducao;
public class GerenciadorColaboradores {
    
    //Adiciona o cadastro dos colaboradores dentro de um ArrayList
    private List<Colaborador> colaboradores = new ArrayList<>();

    public boolean matriculaExistente (int matricula) throws SQLException{
        String sqlMatriculaExistente = "SELECT 1 FROM colaboradores WHERE matricula = ? LIMIT 1"; //Ter ao menos 1 linha com esta matrícula dentro da tabela

              try (Connection connection = ConexaoBanco.getConnection();
                  PreparedStatement statementMatriculaExistente = connection.prepareStatement(sqlMatriculaExistente)) {

                    statementMatriculaExistente.setInt(1, matricula);

                        try (ResultSet resultSetMatriculaExistente = statementMatriculaExistente.executeQuery()) {
                            //Lança true se achar ao menos uma linha com esta matricula e e false se não achar nenhuma
                            return resultSetMatriculaExistente.next(); 
                        }
                }
    }

    //Para adicionar um novo colaborador é necessário que a sua matrícula não esteja castrada no sistema
    public void adicionarColaborador (Colaborador colaborador) throws SQLException {
        
        //Foi colocado ?, ou seja, uma posições em branco dentro de values por questões de maior segurança e fazer com que os dados sejam enviados ao banco no formato correto
        String sqlBase = "INSERT INTO colaboradores (matricula, nome, tipo, salario) VALUES (?, ?, ?, ?)";

        Connection connetion = ConexaoBanco.getConnection();

            try {
                connetion.setAutoCommit(false);

                try (PreparedStatement statementBase = connetion.prepareStatement(sqlBase)) {
                    statementBase.setInt(1, colaborador.getMatricula());
                    statementBase.setString(2, colaborador.getNome());
                    statementBase.setString(3, colaborador.getTipoColaborador());
                    statementBase.setDouble(4, colaborador.getSalario());
                    statementBase.executeUpdate();

                    //Foi colocado ?, ou seja, uma posições em branco dentro de values por questões de maior segurança e fazer com que os dados sejam enviados ao banco no formato correto
                    String sqlComisisonado = "INSERT INTO comissionados (matricula, valor_vendas, percentual_comissao) VALUES (?, ?, ?)";

                    //É feito instanceof pois ao cadastrar um colaborador o programa verifica se o mesmo é do tipo comissionado ou produção para ser colocado em sua respectiva tabela
                    if (colaborador instanceof ColaboradorComissionado) {
                        ColaboradorComissionado colaboradorComissionado = (ColaboradorComissionado) colaborador;

                        try (PreparedStatement statementComissionado = connetion.prepareStatement(sqlComisisonado)) {
                            statementComissionado.setInt(1, colaboradorComissionado.getMatricula());
                            statementComissionado.setDouble(2, colaboradorComissionado.getValorVendas());
                            statementComissionado.setDouble(3, colaboradorComissionado.getPercentualComissao());
                            statementComissionado.executeUpdate();
                        }
                    }

                    //Foi colocado ?, ou seja, uma posições em branco dentro de values por questões de maior segurança e fazer com que os dados sejam enviados ao banco no formato correto
                    String sqlProducao = "INSERT INTO producao (matricula, quantidade_produzida, valor_unidade) VALUES (?, ?, ?)";
                    
                    //É feito instanceof pois ao cadastrar um colaborador o programa verificara se o mesmo é do tipo comissionado ou produção para ser colocado em sua respectiva tabela
                    if (colaborador instanceof ColaboradorProducao) {
                        ColaboradorProducao colaboradorProducao = (ColaboradorProducao) colaborador;

                        try (PreparedStatement statementProducao = connetion.prepareStatement(sqlProducao)) {
                            statementProducao.setInt(1, colaboradorProducao.getMatricula());
                            statementProducao.setInt(2, colaboradorProducao.getQuantidadeProduzida());
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

    public List<Colaborador> getColaboradores() throws SQLException {
        List<Colaborador> lista = new ArrayList<>();
            String sqlBase = "SELECT * FROM colaboradores";

            try (Connection connection = ConexaoBanco.getConnection();
                PreparedStatement statementPadrao = connection.prepareStatement(sqlBase);
                ResultSet resultSetPadrao = statementPadrao.executeQuery()) {

                    while (resultSetPadrao.next()) {
                        int matricula = resultSetPadrao.getInt("Matricula");
                        String nome = resultSetPadrao.getString("Nome");
                        String tipo = resultSetPadrao.getString("Tipo");
                        Double salario = resultSetPadrao.getDouble("Salario");

                        Colaborador colaborador = null;

                        if (tipo.equals("Padrão")) {
                            colaborador = new ColaboradorPadrao(nome, matricula, salario);

                        //No comissionado não utilizei while pois matricula já é a chave primária na tabela comissionados, com isso só é possível termos uma linha com aquela matrícula 
                        } else if (tipo.equals("Comissionado")) {
                            String sqlComisionado = "SELECT * FROM comissionados WHERE matricula = ?";

                            try (PreparedStatement statementComissionado = connection.prepareStatement(sqlComisionado)) {
                                statementComissionado.setInt(1, matricula);

                                try (ResultSet resultSetComissionado = statementComissionado.executeQuery()) {
                                    if(resultSetComissionado.next()) {
                                        double valorVendas = resultSetComissionado.getDouble("valor_vendas");
                                        double percentualComissao = resultSetComissionado.getDouble("percentual_comissao");
                                        colaborador = new ColaboradorComissionado(nome, matricula, salario, valorVendas, percentualComissao);
                                    }
                                }
                            }

                        //No produção não utilizei while pois matricula já é a chave primária na tabela comissionados, com isso só é possível termos uma linha com aquela matrícula
                        } else if (tipo.equals("Produção")) {
                            String sqlProducao = "SELECT * FROM producao WHERE matricula = ?";

                            try (PreparedStatement statementProducao = connection.prepareStatement(sqlProducao)) {
                                statementProducao.setInt(1, matricula);

                                try (ResultSet resultSetProducao = statementProducao.executeQuery()) {
                                    if(resultSetProducao.next()) {
                                        int quantidadeProduzida = resultSetProducao.getInt("quantidade_produzida");
                                        double valorUnidade = resultSetProducao.getDouble("valor_unidade");
                                        colaborador = new ColaboradorProducao(nome, matricula, salario, quantidadeProduzida, valorUnidade);
                                    }
                                }
                            }
                        }

                        lista.add(colaborador);
                    }
            }
            
        return lista;
    }

    //Um colaborador só é removido do sistema se a matricula do mesma estiver castrada
    public boolean removerColaborador(int matricula) throws SQLException {

        Connection connection = ConexaoBanco.getConnection();

        try {
            connection.setAutoCommit(false);

                int linhasApagadas;
                String sqlComissionado = "DELETE FROM comissionados WHERE matricula = ?";
                    try (PreparedStatement statementComissionado = connection.prepareStatement(sqlComissionado)) {
                        statementComissionado.setInt(1, matricula);
                            linhasApagadas = statementComissionado.executeUpdate();

                        String sqlProducao = "DELETE FROM producao WHERE matricula = ?";
                            try (PreparedStatement statementProducao = connection.prepareStatement(sqlProducao)) {
                                statementProducao.setInt(1, matricula);
                                    linhasApagadas = statementProducao.executeUpdate();
                            }

                        String sqlBase = "DELETE FROM colaboradores WHERE matricula = ?";
                            try (PreparedStatement statementBase = connection.prepareStatement(sqlBase)) {
                                statementBase.setInt(1, matricula);
                                    linhasApagadas = statementBase.executeUpdate();
                            }
                    }
                    connection.commit();
                    //este return lança um true se alguma linha da tabela de Colaboradores foi apagada por conta da matrícula existir e um false se nada for apagado
                        return linhasApagadas > 0;
            
        } catch (SQLException e) {
            connection.rollback();
                throw e;
        } finally {
            connection.close();
        }
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

