# Sistema de Pagamento

# Descrição
O Sistema de Folha de Pagamento é uma aplicação desenvolvida em Java com o objetivo de realizar o gerenciamento de colaboradores e automatizar o processo de cálculo salarial. O sistema permite cadastrar, consultar, atualizar e remover colaboradores, além de gerar folhas de pagamento detalhadas e resumos consolidados.
O sistema trabalha com três categorias de colaboradores:
- Colaborador Padrão
- Colaborador Comissionado
- Colaborador por Produção
Todas as informações são armazenadas em banco de dados SQLite.

# Recursos
- Cadastro de colaboradores
- Consulta de colaboradores cadastrados
- Atualização de colaboradores
- Remoção de colaboradores
- Cadastro de colaboradores padrão
- Cadastro de colaboradores comissionados
- Cadastro de colaboradores por produção
- Cálculo automático de salários
- Geração da folha de pagamento
- Emissão de resumo da folha
- Persistência de dados com SQLite
- Validação de regras de negócio
- Arquitetura organizada em camadas
- Testes unitários e de integração
- Cobertura de código com JaCoCo
- Estrutura preparada para expansão de novos tipos de remuneração

# Tecnologias
- Java 17
- Maven
- Git
- SQLite via JDBC
- JOption Pane
- JUnit 5
- JaCoCo

# Pré-requisitos
- JDK 17
- Maven 3.6+
- SQLite
- IDE (VSCode,IntelliJ, NetBeans)

# Como Executar (Maven)
1. Compilar: mvn compile
2. Compilar do Zero: mvn clean compile
3. Executar Testes: mvn test
4. Gerar Pacote: mvn package
5. Executar Aplicação: java -jar target/folha-pagamento.jar

# Configuração
- Banco de Dados
o sistema utiliza SQLite para armazenamento persistente
- Arquivo criado automaticamente:
  - folhaPagamento.db
- String de conexão utilizada:
  - jdbc:sqlite:folhaPagamento.db
O banco de dados e suas tabelas são criados automaticamente quando a aplicação é executada pela primeira vez.

# Funcionalidades
- Cadastro de colaboradores:
Permite cadastrar três tipos de colaboradores, que seriam:
- Colaborador padrão: 
  - Recebe apenas o salário base
Exemplo:
Matrícula: 1001
Nome: João Silva
- Colaborador comissionado: 
  - Recebe o salário base e comissão sobre vendas
Exemplo:
Matrícula: 1002
Nome: Maria Souza
Salário Base: R$ 2500,00
Vendas: R$ 10.000,00
Comissão: 10%
- Colaborador por produção: 
  - Recebe o salário base e um adicional por produção
Exemplo:
Matrícula: 1003
Nome: Carlos Oliveira
Salário Base: R$ 2500,00
Quantidade Produzida: 200
Valor por Unidade: R$ 5,00
- Consulta de colaboradores: 
  - Lista todos os colabores cadastrados que tenham:
Matrícula,
Nome,
Tipo de colaborador,
Remuneração
- Atualização de colaboradores: 
  - Permite alterar:
Nome,
Salário Base,
Informações específicas do tipo de colaborador
- Remoção de colaboradores:
Permite excluir colaboradores utilizando a matrícula
- Geração de folha de pagamento:
Matrícula, 
Nome, 
Tipo de Colaborador, 
Salário Final
Exemplo:
 1001 | João Silva | Padrão | R$ 2.500,00
1002 | Maria Souza | Comissionado | R$ 3.500,00
1003 | Carlos Oliveira | Produção | R$ 3.500,00
- Resumo da folha: apresenta a quantidade de colaboradores e o valor total da folha de pagamento
- Regras de negócio: implementa as seguintes validações:
  - Matrícula única
  - Nome obrigatório
  - Nome completo obrigatório
  - Matrícula não pode ser negativa
  - Salário não pode ser negativo
  - Valor de vendas não pode ser negativo
  - Percentual de comissão não pode ser negativo
  - Quantidade produzida não pode ser negativa
  - Valor por unidade produzida não pode ser negativo
  - Cálculo automático dos salários

# Estrutura do Projeto
- src/main/java

folhapagamento

- model
  - Colaborador.java
  - ColaboradorPadrao.java
  - ColaboradorComissionado.java
  - ColaboradorProducao.java
- repositorio
  - GerenciadorColaboradores.java
- service
  - Relatorio.java
- dao
  - ConexaoBanco.java
- view
  - Cadastro.java
- Main.java

# Testes
Executar testes:
mvn test
Tipos de testes implementados:
- Testes Unitários
- Testes de Integração
- Testes de Sistema
- Testes de Aceitação
Cobertura de código medida através do JaCoCo.

# Boas Práticas e Contribuições
Contribuições são bem-vindas.
Sugestões:
- Abrir uma issue descrevendo a melhoria.
- Criar uma branch descritiva.
- Realizar commits pequenos.
- Abrir Pull Request documentando as mudanças.
- Garantir que os testes estejam aprovados.
Modelo de Commit
- feat: adicionar cadastro de colaborador de produção
- feat: implementar persistência em SQLite
- fix: corrigir validação de matrícula duplicada
- fix: corrigir cálculo de comissão
- docs: atualizar README
- test: adicionar testes unitários do relatório

# Equipe
Projeto desenvolvido por:
- Gabriel Gastal
- Joaquim Attanasio da Silva
- Pedro Ascenço Bueno
- Disciplina: Garantia da Qualidade de Software (GQS)
- Instituição: Centro Universitário Ritter dos Reis - Campus FAPA

# Licença
Este projeto foi desenvolvido exclusivamente para fins acadêmicos como parte das atividades avaliativas da disciplina de Garantia da Qualidade de Software.

# Contato
 Curso: Ciências da computação
 Instituição: Centro Universitário Ritter dos Reis - Campus FAPA
