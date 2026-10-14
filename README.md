# Sistema de Pagamento

# Descrição
O Sistema de Folha de Pagamento é uma aplicação desenvolvida em Java com o objetivo de realizar o gerenciamento de colaboradores e automatizar o processo de cálculo salarial. O sistema permite cadastrar, consultar, atualizar e remover colaboradores, além de gerar folhas de pagamento detalhadas e resumos consolidados.
O sistema trabalha com três categorias de colaboradores:
- - Colaborador Padrão
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
String de conexão utilizada:
jdbc:sqlite:folhaPagamento.db
O banco de dados e suas tabelas são criados automaticamente quando a aplicação é executada pela primeira vez.
