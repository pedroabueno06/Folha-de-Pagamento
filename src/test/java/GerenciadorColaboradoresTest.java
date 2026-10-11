import java.nio.file.Path;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir; //Entrega uma pasta nova para cada teste, e apagando no fim dos mesmos

import com.example.folhaPagamento.dao.ConexaoBanco;
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
}
