//Entrega uma pasta nova para cada teste, e apagando no fim dos mesmos
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.example.folhaPagamento.dao.ConexaoBanco;
import com.example.folhaPagamento.model.Colaborador;
import com.example.folhaPagamento.model.ColaboradorComissionado;
import com.example.folhaPagamento.model.ColaboradorPadrao;
import com.example.folhaPagamento.model.ColaboradorProducao;
import com.example.folhaPagamento.repositorio.GerenciadorColaboradores;
public class GerenciadorColaboradoresTest {
    private GerenciadorColaboradores gerenciadorColaboradores;

    @BeforeEach //Roda antes dos testes iniciarem
    public void preparaBancoDeDadosTeste(@TempDir Path tempDir) throws SQLException {
        System.setProperty("folha.db.url", "jdbc:sqlite:" + tempDir.resolve("teste.db"));
            ConexaoBanco.criarTabelas();
                gerenciadorColaboradores = new GerenciadorColaboradores();

    }
    @AfterEach //Roda após o final dos testes
    public void limparBancoDeDadosTeste() {
        System.clearProperty("folha.db.url");
    }

    private Colaborador buscarMatricula(int matricula) throws SQLException {
        for (Colaborador colaborador : gerenciadorColaboradores.getColaboradores()) {
            if (colaborador.getMatricula() == matricula) {
                return colaborador;
            }
        }
        return null;
    }

    @Test 
    @DisplayName ("CT001 - Cadastro de Colaborador Padrão")
    public void testarCadastrarColaboradorPadrao() throws SQLException {
        ColaboradorPadrao colaborador = new ColaboradorPadrao("Pedro Bueno", 123, 1500);
            gerenciadorColaboradores.adicionarColaborador(colaborador);
            
            Colaborador colaboradorCadastrado = buscarMatricula(123);
                assertNotNull(colaboradorCadastrado);
                assertEquals("Pedro Bueno", colaboradorCadastrado.getNome());
                assertEquals(123, colaboradorCadastrado.getMatricula());
                assertEquals(1500, colaboradorCadastrado.getSalario(), 0.001);
                assertInstanceOf(ColaboradorPadrao.class, colaboradorCadastrado);
        }

    @Test 
    @DisplayName ("CT002 – Cadastro de Colaborador Comissionado")
    public void testarCadastrarColaboradorComissionado() throws SQLException {
        ColaboradorComissionado colaboradorComissionado= new ColaboradorComissionado("Pedro Bueno", 123, 1500, 100,10 );
            gerenciadorColaboradores.adicionarColaborador(colaboradorComissionado);

            ColaboradorComissionado colaboradorCadastrado = (ColaboradorComissionado) buscarMatricula(123);
                assertNotNull(colaboradorCadastrado);
                assertEquals("Pedro Bueno", colaboradorCadastrado.getNome());
                assertEquals(123, colaboradorCadastrado.getMatricula());
                assertEquals(1500, colaboradorCadastrado.getSalario(), 0.001);
                assertEquals(100, colaboradorCadastrado.getValorVendas(), 0.001);
                assertEquals(10, colaboradorCadastrado.getPercentualComissao(), 0.001);
                assertInstanceOf(ColaboradorComissionado.class, colaboradorCadastrado);
    }

    @Test 
    @DisplayName ("CT003 – Cadastro de Colaborador por Produção")
    public void testarCadastrarColaboradorProducao() throws SQLException {
        ColaboradorProducao colaboradorProducao = new ColaboradorProducao("Pedro Bueno", 123, 1500, 100,10);
            gerenciadorColaboradores.adicionarColaborador(colaboradorProducao);

            ColaboradorProducao colaboradorCadastrado = (ColaboradorProducao) buscarMatricula(123);
                assertNotNull(colaboradorCadastrado);
                assertEquals("Pedro Bueno", colaboradorCadastrado.getNome());
                assertEquals(123, colaboradorCadastrado.getMatricula());
                assertEquals(1500, colaboradorCadastrado.getSalario(), 0.001);
                assertEquals(100, colaboradorCadastrado.getQuantidadeProduzida(), 0.001);
                assertEquals(10, colaboradorCadastrado.getValorUnidade(), 0.001);
                assertInstanceOf(ColaboradorProducao.class, colaboradorCadastrado);
    }

    @Test 
    @DisplayName ("CT004 – Matrícula Duplicada")
    public void testarMatriculaDuplicada() throws SQLException {
        gerenciadorColaboradores.adicionarColaborador(new ColaboradorPadrao("Pedro Bueno", 123, 1500));
        assertTrue(gerenciadorColaboradores.matriculaExistente(123));
        assertFalse(gerenciadorColaboradores.matriculaExistente(1234));

        Colaborador duplicado = new ColaboradorPadrao("Anna Clara Vianna de Bairros", 123, 1600);
            assertThrows(SQLException.class, ()
                -> gerenciadorColaboradores.adicionarColaborador(duplicado));

                List<Colaborador> lista = gerenciadorColaboradores.getColaboradores();
                    assertEquals(1, lista.size());
                    assertEquals("Pedro Bueno", lista.get(0).getNome());
                    assertInstanceOf(ColaboradorPadrao.class, lista.get(0));
    }

    @Test 
    @DisplayName ("CT013 – Consulta de Colaboradores")
    public void testarConsultarColaboradores() throws SQLException {
        gerenciadorColaboradores.adicionarColaborador(new ColaboradorPadrao("Pedro Bueno", 123, 1500));
        gerenciadorColaboradores.adicionarColaborador(new ColaboradorComissionado("Anna Clara Vianna de Bairros", 456, 1600, 100, 10));
        gerenciadorColaboradores.adicionarColaborador(new ColaboradorProducao("Miguel Ascenço Bueno", 789, 1700, 100, 10));

        List<Colaborador> lista = gerenciadorColaboradores.getColaboradores();
            assertEquals(3, lista.size());
            assertInstanceOf(ColaboradorPadrao.class, lista.get(0));
            assertInstanceOf(ColaboradorComissionado.class, lista.get(1));
            assertInstanceOf(ColaboradorProducao.class, lista.get(2));
    }

    @Test 
    @DisplayName ("CT014 – Consulta Sem Colaboradores Cadastrados")
    public void testarConsultarColaboradoresSemCadastro() throws SQLException {
        List<Colaborador> lista = gerenciadorColaboradores.getColaboradores();
            assertEquals(0, lista.size());
    }

    @Test 
    @DisplayName ("CT020 – Atualizar Colaborador")
    public void testarAtualizarColaborador() throws SQLException {
        gerenciadorColaboradores.adicionarColaborador(new ColaboradorPadrao("Pedro Bueno", 123, 1500));

        gerenciadorColaboradores.atualizarColaborador(123,
            new ColaboradorPadrao("Pedro Ascenço Bueno", 123, 1600));

            Colaborador atualizarColaborador = buscarMatricula(123);
                assertNotNull(atualizarColaborador);
                assertEquals("Pedro Ascenço Bueno", atualizarColaborador.getNome());
                assertEquals(123, atualizarColaborador.getMatricula());
                assertEquals(1600, atualizarColaborador.getSalario(), 0.001);
                assertEquals(1, gerenciadorColaboradores.getColaboradores().size());
    }

    @Test 
    @DisplayName ("CT021 – Remover Colaborador")
    public void testarRemoverColaborador() throws SQLException {
        gerenciadorColaboradores.adicionarColaborador(new ColaboradorPadrao("Pedro Bueno", 123, 1500));

            gerenciadorColaboradores.removerColaborador(123);

            Colaborador colaboradorRemovido = buscarMatricula(123);
                assertNull(colaboradorRemovido);
    }
}
