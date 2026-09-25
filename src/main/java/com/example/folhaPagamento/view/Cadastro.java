package com.example.folhaPagamento.view;

import java.util.List;

import javax.swing.JOptionPane;

import com.example.folhaPagamento.model.Colaborador;
import com.example.folhaPagamento.model.ColaboradorComissionado;
import com.example.folhaPagamento.model.ColaboradorPadrao;
import com.example.folhaPagamento.model.ColaboradorProducao;
import com.example.folhaPagamento.repositorio.GerenciadorColaboradores;

public class Cadastro {

    private GerenciadorColaboradores gerenciador;

    public Cadastro(GerenciadorColaboradores gerenciador) {
        this.gerenciador = gerenciador;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            int escolha = exibirMenu();
            switch (escolha) {
                case 0: //Cadastrar colaborador
                cadastrarColaborador();
                    break;
                case 1: //Listar Colaboradores
                listarColaboradores();
                    break;
                case 2: //Atualizar Colaborador
                colaboradorAtualizado();
                    break;
                case 3: //Remover Colaborador
                colaboradorRemovido();
                    break;
                case 4: //Gerar Folha de pagamento
                    break;
                case 5:
                case JOptionPane.CLOSED_OPTION:
                    continuar = false;
                    break;
                default:
                    break;
            }
        }
    }

    private int exibirMenu() {
        Object[] opcoes = {"Cadastrar Colaborador",
            "Listar Colaboradores",
            "Atualizar Colaborador",
            "Remover Colaborador",
            "Gerar Folha de Pagamento",
            "Sair"};

        return JOptionPane.showOptionDialog(null,
                "Selecione a opção desejada:", //Mensagem de entrada
                "======================================================= FOLHA DE PAGAMENTO ===================================================", //Título da janela
                JOptionPane.DEFAULT_OPTION, //Tipo de Opção
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes, //Botões em ordem
                opcoes[0]);
    }

    private void cadastrarColaborador() {
        String tipo = lerTipoColaborador();
        if (tipo == null) {
            return;
        }

        String nome = lerNome();
        if (nome == null) {
            return;
        }

        Integer matricula = lerMatricula();
        if (matricula == null) {
            return;
        }

        Double salario = lerSalario();
        if (salario == null) {
            return;
        }

        Colaborador novoColaborador = null;

        if (tipo.equals("Padrão")) {
            novoColaborador = new ColaboradorPadrao(nome, matricula, salario);

        } else if (tipo.equals("Comissionado")) {
            
            Double vendas = valorVendas();
            if (vendas == null)
            return;

            Double percentual = percentualComissao();
            if (percentual == null)
            return;

            novoColaborador = new ColaboradorComissionado(nome, matricula, salario, vendas, percentual);

        } else if (tipo.equals("Produção")) {
            
            Integer producao = quantidadeProduzida();
            if(producao == null)
            return;

            Double unidade = valorUnidade();
            if (unidade == null)
            return;
            
            novoColaborador = new ColaboradorProducao(nome, matricula, salario, producao, unidade);

        }

        try {
            gerenciador.adicionarColaborador(novoColaborador);
            JOptionPane.showMessageDialog(null, "Colaboador cadastrado com sucesso!");
        
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    private String lerTipoColaborador() {
        String[] opcoes = {"Padrão", "Comissionado", "Produção"};

        String tipoEscolhido = (String) JOptionPane.showInputDialog(
                null,
                "Selecione o tipo de colaborador: ",
                "Tipo de colaborador: ",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        return tipoEscolhido;
    }

    private String lerNome() {
        String nomeCompleto = "";
        boolean nomeValido = false;

        //Loop de para ver se o usuário digitou um nome válido
        while (!nomeValido) {
            nomeCompleto = JOptionPane.showInputDialog(null, "Digite o nome do colaborador: ",
                                                    "", JOptionPane.QUESTION_MESSAGE);

            //Verifica se o usuário encerrou o programa.
            if (nomeCompleto == null) {
                return null;
            }

            //Faz a remoção de espaços em branco que podem estar no início ou no fim do nome digitado.
            nomeCompleto = nomeCompleto.trim();

            if (nomeCompleto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome Inválido! Seu nome não pode estar em branco!",
                                            "", JOptionPane.ERROR_MESSAGE);

            } else if (nomeCompleto.split("\\s+").length < 2) {
                JOptionPane.showMessageDialog(null, "Nome Inválido! Você deve informar seu nome completo.",
                                            "", JOptionPane.ERROR_MESSAGE);
            
            } else {
                nomeValido = true;
            }
        }

        return nomeCompleto;
    }

    private Integer lerMatricula() {
        while (true) {
            String texto = JOptionPane.showInputDialog("Digite sua matrícula: ");

            //Verifica se o usuário encerrou o programa.
            if (texto == null) {
                return null;
            }

            try {
                int numeroMatricula = Integer.parseInt(texto);
                if (numeroMatricula < 0) {
                    JOptionPane.showMessageDialog(null, "O número da sua matrícula não deve ser negativo!",
                                                "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                if (gerenciador.matriculaExistente(numeroMatricula)) {
                    JOptionPane.showMessageDialog(null, "Matrícula inválida! Digite um número de matrícula que não esteja em uso.",
                                                "", JOptionPane.ERROR_MESSAGE);
                    
                    continue;
                }
                return numeroMatricula;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Matrícula inválida! Digite um número inteiro.",
                                            "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Double lerSalario() {
        String texto = "";

        while (true) {
            texto = JOptionPane.showInputDialog("Digite seu salário (Ao invés de usar vírgula, utilize ponto ex: 1.5): ");

            //Verifica se o usuário encerrou o programa.
            if (texto == null) {
                return null;
            }

            try {
                double salario = Double.parseDouble(texto);

                if (salario < 0) {
                    JOptionPane.showMessageDialog(null, "Salário inválido! Seu salário não pode ser negativo.",
                                                "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return salario;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Salário Inválido! Seu salário não pode possuir possuir vírgulas, apenas pontos (ex: 1.5).",
                                            "", JOptionPane.ERROR_MESSAGE);
                continue;
            }
        }
    }

    private Double valorVendas() {
        while (true) {
            String valor = JOptionPane.showInputDialog("Digite o valor total das suas vendas (Ao invés de usar vírgula, utilize ponto ex: 1.5):");

            //Verifica se o usuário encerrou o programa.
            if (valor == null) {
                return null;
            }

            try {
                double vendas = Double.parseDouble(valor);

                if (vendas < 0) {
                    JOptionPane.showMessageDialog(null, "Valor de vendas inválido! Seu valor de vendas não pode ser negativo.",
                                                "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return vendas;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor de vendas inválido! Seu valor de vendas não pode possuir vírgulas, apenas pontos (ex: 1.5).",
                                            "", JOptionPane.ERROR_MESSAGE);
                continue;
            }

        }
    }

    private Double percentualComissao() {
        while (true) {
            String percentual = JOptionPane.showInputDialog(null, "Digite o percentual das suas comissões (Ao invés de usar vírgula, utilize ponto ex: 1.5): ");

            //Verifica se o usuário encerrou o programa.
            if (percentual == null) {
                return null;
            }

            try {
                double comissao = Double.parseDouble(percentual);

                if (comissao < 0) {
                    JOptionPane.showMessageDialog(null, "Percentual de comsissão inválido! O percentual de suas vendas não pode ser negativo.",
                                                "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return comissao;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Percentual de comissão inválido! O percentual de suas vendas não pode possuir vírgulas, apenas pontos (ex: 1.5).",
                                            "", JOptionPane.ERROR_MESSAGE);
                continue;
            }
        }
    }

    private Integer quantidadeProduzida() {
        while (true) {
            String quantidade = JOptionPane.showInputDialog(null, "Digite a quantidade a quantidade produzida: ");

            //Verifica se o usuário encerrou o programa.
            if (quantidade == null) {
                return null;
            }

            try {
                int producao = Integer.parseInt(quantidade);

                if (producao < 0) {
                    JOptionPane.showMessageDialog(null, "Quantidade produzida inválida! Sua quantidade produzida não pode ser negativa.",
                                            "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return producao;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantidade produzida invalida! Sua quantidade produzida não pode possuir vírgulas ou pontos.",
                                            "", JOptionPane.ERROR_MESSAGE);
                continue;
            }
        }
    }

    private Double valorUnidade() {
        while (true) { 
            String valor = JOptionPane.showInputDialog(null, "Digite o valor das unidades produzidas (Ao invés de usar vírgula, utilize ponto (ex: 1.5): ");

            //Verifica se o usuário encerrou o programa.
            if (valor == null) {
                return null;
            }

            try {
                double unidade = Double.parseDouble(valor);

                if (unidade < 0) {
                    JOptionPane.showMessageDialog(null, "Valor de unidade produzida inválido! O valor não pode ser negativo.",
                                            "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return unidade;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor de unidade produzida inválido! O valor de unidade não pode possuir vírgulas.",
                                            "", JOptionPane.ERROR_MESSAGE);
                continue;
            }
        }
    }

    private void listarColaboradores() {
        List<Colaborador> colaboradores = gerenciador.getColaboradores();

        if (colaboradores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador cadastrado!",
                                        "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("--- Lista de Colaboradores ---");
        for (Colaborador colaborador : colaboradores) {
            System.out.println(colaborador);
        }
    }

    public Integer lerMatriculaExistente() {
        while (true) {
            String texto = JOptionPane.showInputDialog(null,"Digite a matrícula do colaborador:",
                                                    "", JOptionPane.QUESTION_MESSAGE);
            
            //Verifica se o usuário encerrou o programa.
            if (texto == null) {
                return null;
            }

            try {
                int numeroMatricula = Integer.parseInt(texto);

                if(numeroMatricula < 0) {
                    JOptionPane.showMessageDialog(null, "Matrícula inválida! Sua matrícula não pode ser negativa.",
                                                "", JOptionPane.ERROR_MESSAGE);
                continue;
                
                }

                if(!gerenciador.matriculaExistente(numeroMatricula)) {
                    JOptionPane.showMessageDialog(null, "Matrícula inválida! Nenhum colaborador foi encontrado com este número de matrícula.",
                                                "", JOptionPane.ERROR_MESSAGE);
                continue;

                }
                return numeroMatricula;


            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Matrícula Inválida! Digite um número inteiro",
                                            "", JOptionPane.ERROR_MESSAGE);
            
            }
        }
    }

    private void colaboradorAtualizado() {

            Integer matricula = lerMatriculaExistente();
            if (matricula == null) {
                return;
            }

            String tipo = lerTipoColaborador();
            if(tipo == null) {
                return;
            }

            String nome = lerNome();
            if(nome == null) {
                return;
            }

            Colaborador atualizarColaborador = null;

            Double salario = lerSalario();
            if (salario == null) {
                return;
            }

            if (tipo.equals("Padrão")) {
                atualizarColaborador = new ColaboradorPadrao(nome, matricula, salario);
            
            } else if (tipo.equals("Comissionado")) {

                Double vendas = valorVendas();
                if (vendas == null) {
                    return;
                }

                Double percentual = percentualComissao();
                if (percentual == null) {
                    return;
                }

                atualizarColaborador = new ColaboradorComissionado(nome, matricula, salario, vendas, percentual);
                
            }

            if(tipo.equals("Produção")) {

                Integer producao = quantidadeProduzida();
                if(producao == null)
                return;

                Double unidade = valorUnidade();
                if(unidade == null)
                return;

                atualizarColaborador = new ColaboradorProducao(nome, matricula, salario, producao, unidade);
            }
            
            try {
                gerenciador.atualizarColaborador(matricula,atualizarColaborador);
                    JOptionPane.showMessageDialog(null, "Colaborador atualizado com sucesso!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
    }

    private void colaboradorRemovido() {
        Integer matricula = lerMatriculaExistente();

        if(matricula == null) {
            return;
        }

        gerenciador.removerColaborador(matricula);
        JOptionPane.showMessageDialog(null, "Colaborador removido com sucesso!");
    }

}