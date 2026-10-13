import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.folhaPagamento.model.Colaborador;
import com.example.folhaPagamento.model.ColaboradorComissionado;
import com.example.folhaPagamento.model.ColaboradorPadrao;
import com.example.folhaPagamento.model.ColaboradorProducao;
import com.example.folhaPagamento.service.Relatorio;

public class RelatorioTest {
    private Relatorio relatorio;
    private List<Colaborador> colaboradores;
    
    @BeforeEach //Roda antes dos testes iniciarem
    public void criarColaboradores() {
        relatorio = new Relatorio();
        colaboradores = new ArrayList<>();
        colaboradores.add(new ColaboradorPadrao("Pedro Bueno", 123, 1500));
        colaboradores.add(new ColaboradorComissionado("Anna Clara Vianna de Bairros", 456, 1600, 100, 10));
        colaboradores.add(new ColaboradorProducao("Miguel Ascenço Bueno", 789, 1700, 100, 10));
    }

    @Test 
    @DisplayName ("CT018 – Geração da Folha de Pagamento")
    public void testarGerarFolhaPagamento() {
        String gerarFolhaPagamento = relatorio.relatorioDetalhado(colaboradores);

        assertTrue(gerarFolhaPagamento.contains("FOLHA DE PAGAMENTO DETALHADA"));
        String[] informacoesColaboradores = {
            "Pedro Bueno", "123", NumberFormat.getInstance().format(1500), 
            "Anna Clara Vianna de Bairros", "456", NumberFormat.getInstance().format(1600), NumberFormat.getInstance().format(100), NumberFormat.getInstance().format(10), 
            "Miguel Ascenço Bueno", "789", NumberFormat.getInstance().format(1700), NumberFormat.getInstance().format(100), NumberFormat.getInstance().format(10)
        };
        
        for (String info : informacoesColaboradores) {
            assertTrue(gerarFolhaPagamento.contains(info), "Faltaram as seguintes informações na folha de pagamento: " + info);
        }


    }

    @Test
    @DisplayName ("CT019 – Resumo da Folha de Pagamento")
    public void testarGerarResumoFolhaPagamento() {

        //Total da folha de pagamento
        assertEquals(5810, relatorio.calcularTotalFolha(colaboradores), 0.001);
    }

    @Test
    @DisplayName ("CT019 – Resumo da Folha de Pagamento")
    public void testarGerarResumoFolhaPagamentoPorTipo() {

        //Total pago a cada tipo de colaborador
        assertEquals(1500, relatorio.calcularTotalPorTipo(colaboradores, "Padrão"), 0.001);
        assertEquals(1610, relatorio.calcularTotalPorTipo(colaboradores, "Comissionado"), 0.001);
        assertEquals(2700, relatorio.calcularTotalPorTipo(colaboradores, "Produção"), 0.001);
    }

    @Test
    @DisplayName ("CT019 – Resumo da Folha de Pagamento")
    public void testarGerarResumoFolhaPagamentoQuantidadeColaboradoresCadastrados() {
        //Quantidade de colaboradores cadastrados
        assertEquals(3, colaboradores.size());
    }
}