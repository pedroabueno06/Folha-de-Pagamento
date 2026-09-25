package com.example.folhaPagamento.view;

import javax.swing.JOptionPane;

import com.example.folhaPagamento.model.Colaborador;
import com.example.folhaPagamento.model.ColaboradorPadrao;
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
                    break;
                case 1: //Listar Colaboradores
                    break;
                case 2: //Atualizar Colaborador
                    break;
                case 3: //Remover Colaborador
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
            nomeCompleto = JOptionPane.showInputDialog(null, "Digite o nome do colaborador:",
                    "Validação de Nome", JOptionPane.QUESTION_MESSAGE);

            //Verifica se o usuário encerrou o programa.
            if (nomeCompleto == null) {
                return null;
            }

            //Faz a remoção de espaços em branco que podem estar no início ou no fim do nome digitado.
            nomeCompleto = nomeCompleto.trim();

            if (!nomeCompleto.isEmpty() && nomeCompleto.length() >= 2) {
                nomeValido = true;

            } else {
                JOptionPane.showMessageDialog(null, "Nome Inválido! Seu nome não deve estar em branco, além de ter que informar seu nome completo.",
                        "Nome Inválido", JOptionPane.ERROR_MESSAGE);
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
                return numeroMatricula;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Matrícula inválida! Digite um número inteiro.",
                        "Matrícula inválida", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Salário inválido! Seu salário não pode ser negativo.");
                    continue;
                }

                return salario;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Salário inválido. Digite um valor utilizando ponto ao invés de vírgula (ex: 1.5).");
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
                    JOptionPane.showMessageDialog(null, "Valor de vendas inválido! Seu valor de vendas não pode ser negativo.");
                    continue;
                }
                return vendas;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor de vendas inválido! Digite um valor utilizando ponto ao invés de vírgula (ex: 1.5).");
                continue;
            }

        }
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

        } else if (tipo.equals("Produção")) {

        }

        try {
            gerenciador.adicionarColaborador(novoColaborador);
            JOptionPane.showMessageDialog(null, "Colaboador cadastrado com sucesso");
        
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
}