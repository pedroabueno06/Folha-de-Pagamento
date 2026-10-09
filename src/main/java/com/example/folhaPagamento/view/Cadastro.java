package com.example.folhaPagamento.view;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JOptionPane;

import com.example.folhaPagamento.model.Colaborador;
import com.example.folhaPagamento.model.ColaboradorComissionado;
import com.example.folhaPagamento.model.ColaboradorPadrao;
import com.example.folhaPagamento.model.ColaboradorProducao;
import com.example.folhaPagamento.repositorio.GerenciadorColaboradores;
import com.example.folhaPagamento.service.Relatorio;

public class Cadastro {

    private GerenciadorColaboradores gerenciador;
    private Relatorio relatorio = new Relatorio();


    public Cadastro(GerenciadorColaboradores gerenciador) {
        this.gerenciador = gerenciador;
    }

    //Inicia tela do JOptionPane
    public void iniciar() {
        boolean continuar = true;

        //Loop com as opções em relação a folha de pagamento
        while (continuar) {
            int escolha = exibirMenu();
            switch (escolha) {
                case 0: //Cadastrar colaborador
                cadastrarColaborador();
                    break;
                case 1: //Listar Colaboradores
                limparTela();
                    listarColaboradores();
                        break;
                case 2: //Atualizar Colaborador
                colaboradorAtualizado();
                    break;
                case 3: //Remover Colaborador
                colaboradorRemovido();
                    break;
                case 4: //Gerar Folha de pagamento
                limparTela();
                    gerarFolhaPagamentoDetalhada();
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

    //Opções mostradas ao usuário
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

    //Metodo para o usuário cadastrar um tipo de colaborador
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
            JOptionPane.showMessageDialog(null, "Colaborador cadastrado com sucesso!");
        
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados! Por favor tente novamente." + e.getMessage(),
                                        "", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Programa lê o tipo de colaborador e dependendo do tipo  pede os respectivos dados
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
                    JOptionPane.showMessageDialog(null, "Matrícula inválida! O número da sua matrícula não pode ser negativo!",
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

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar ler a matrícula! Por favor tente novamente." + e.getMessage(),
                                            "", JOptionPane.ERROR_MESSAGE);
                continue;
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

                //Verifica se o salário do colaborador não é negativo
                if (salario < 0) {
                    JOptionPane.showMessageDialog(null, "Salário inválido! Seu salário não pode ser negativo.",
                                                "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                //Verifica se salário do colaborador não é infinito
                if (Double.isInfinite(salario)) {
                    JOptionPane.showMessageDialog(null, "Salário inválido! Seu salário não pode ser infinito.",
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
            String valor = JOptionPane.showInputDialog("Digite o valor total das suas vendas (Ao invés de usar vírgula, utilize ponto (ex: 1.5):");

            //Verifica se o usuário encerrou o programa.
            if (valor == null) {
                return null;
            }

            try {
                double vendas = Double.parseDouble(valor);

                //Verifica se o valor das vendas do colaborador não é negativo
                if (vendas < 0) {
                    JOptionPane.showMessageDialog(null, "Valor de vendas inválido! Seu valor de vendas não pode ser negativo.",
                                                "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                //Verifica se o valor das vendas do colaborador não é infinito
                if (Double.isInfinite(vendas)) {
                    JOptionPane.showMessageDialog(null, "Valor de vendas inválido! Seu valor de vendas não pode ser infinito.",
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
            String percentual = JOptionPane.showInputDialog(null, "Digite o percentual da sua comissão (Ao invés de usar vírgula, utilize ponto (ex: 1.5): ");

            //Verifica se o usuário encerrou o programa.
            if (percentual == null) {
                return null;
            }

            try {
                double comissao = Double.parseDouble(percentual);

                //Verifica se o percentual de comissão do colaborador não é negativo
                if (comissao < 0) {
                    JOptionPane.showMessageDialog(null, "Percentual de comsissão inválido! O percentual de suas vendas não pode ser negativo.",
                                                "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                //Verifica se o percentual de comissão do colaborador não é infinito
                if (Double.isInfinite(comissao)) {
                    JOptionPane.showMessageDialog(null, "Percentual de comissão inválido! O percentual da sua comissão não pode ser infinito.",
                                                "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                return comissao;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Percentual de comissão inválido! O percentual da sua comissão não pode possuir vírgulas, apenas pontos (ex: 1.5).",
                                            "", JOptionPane.ERROR_MESSAGE);
                continue;
            }
        }
    }

    private Integer quantidadeProduzida() {
        while (true) {
            String quantidade = JOptionPane.showInputDialog(null, "Digite a quantidade produzida: ");

            //Verifica se o usuário encerrou o programa.
            if (quantidade == null) {
                return null;
            }

            try {
                int producao = Integer.parseInt(quantidade);

                //Verifica se a quantidade produzida do colaborador não é negativa
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
            String valor = JOptionPane.showInputDialog(null, "Digite o valor por unidade produzida (Ao invés de usar vírgula, utilize ponto (ex: 1.5): ");

            //Verifica se o usuário encerrou o programa.
            if (valor == null) {
                return null;
            }

            try {
                double unidade = Double.parseDouble(valor);

                //Verifica se o valor de unidades produzidas do colaborador não é negativo
                if (unidade < 0) {
                    JOptionPane.showMessageDialog(null, "Valor por unidade produzida inválido! O valor não pode ser negativo.",
                                            "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                //Verifica se o valor por unidade produzida do colaborador não é infinito
                if (Double.isInfinite(unidade)) {
                    JOptionPane.showMessageDialog(null, "Valor por unidade produzida inválido! O valor por unidade não pode ser infinito.",
                                                "", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                return unidade;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor por unidade produzida inválido! O valor por unidade não pode possuir vírgulas.",
                                            "", JOptionPane.ERROR_MESSAGE);
                continue;
            }
        }
    }

    //Método que lista todos os colaboradores castradaos no sistema
    private void listarColaboradores() {
        
        try {
            List<Colaborador> colaboradores = gerenciador.getColaboradores();

            //Método que verifica se tem colaboradores castrados no sistema e se não tiver retorna a mensagem abaixo
            if (colaboradores.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum colaborador cadastrado!",
                                            "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            System.out.println("\n------------------------");
            System.out.println("LISTA DE COLABORADORES");
            System.out.println("------------------------\n");
            
            for (Colaborador colaborador : colaboradores) {
                System.out.println(colaborador);
            }

        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar listar os colaboradores cadastrados! Por favor tente novamente." + e.getMessage(),
                                            "", JOptionPane.ERROR_MESSAGE);
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

                //Método que verifica se a matrícula do colaborador já foi cadastrada
                if(!gerenciador.matriculaExistente(numeroMatricula)) {
                    JOptionPane.showMessageDialog(null, "Matrícula inválida! Nenhum colaborador foi encontrado com este número de matrícula.",
                                                "", JOptionPane.ERROR_MESSAGE);
                continue;

                }
                return numeroMatricula;


            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Matrícula Inválida! Digite um número inteiro",
                                            "", JOptionPane.ERROR_MESSAGE);
            
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar ler a existência da matrícula! Por favor tente novamente." + e.getMessage(),
                                            "", JOptionPane.ERROR_MESSAGE);
                continue;
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

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar o cadastro do colaborador! Por favor tente novamente." + e.getMessage(),
                                            "", JOptionPane.ERROR_MESSAGE);
            }
    }

    //Método para remover o colaborador do sistema
    private void colaboradorRemovido() {
        Integer matricula = lerMatriculaExistente();

        if(matricula == null) {
            return;
        }

         int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o colaborador?",
                "", JOptionPane.YES_NO_OPTION);

                    if (opcao != JOptionPane.YES_NO_OPTION) {
                        return;
                    
                    }
        try {
            gerenciador.removerColaborador(matricula);
            
            JOptionPane.showMessageDialog(null, "Colaborador removido com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar remover o cadastro do colaborador! Por favor tente novamente." + e.getMessage(),
                                            "", JOptionPane.ERROR_MESSAGE);
            }
    }

    private void gerarFolhaPagamentoDetalhada() {
        try {
            List<Colaborador> colaboradores = gerenciador.getColaboradores();

                if (colaboradores.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhum colaborador cadastrado!",
                                            "", JOptionPane.ERROR_MESSAGE);

                    return;
                }
                
                String textoRelatorio = relatorio.relatorioDetalhado(colaboradores);
                System.out.println(textoRelatorio);

                Double totalFolha = relatorio.calcularTotalFolha(colaboradores);
                System.out.println("---------------------------------------");
                System.out.println("Total da folha de pagamento: R$ " + NumberFormat.getNumberInstance().format(totalFolha));
                    System.out.println("---------------------------------------");
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar gerar a folha de pagamento! Por favor tente novamente." + e.getMessage(),
                                            "", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparTela() {
        System.out.print("\033[H\033[2J");
            System.out.flush();
    }
}