import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.folhaPagamento.model.ColaboradorProducao;
public class ColaboradorProducaoTest {
    private ColaboradorProducao colaboradorProducao;

    @BeforeEach //Roda antes dos testes iniciarem
    public void criarColaboradorProducao() {
        colaboradorProducao = new ColaboradorProducao("Pedro Bueno", 123, 1500, 100, 10);

    }

    @Test
    @DisplayName("CT011 – Quantidade Produzida Negativa")
    public void testarQuantidadeProduzidaNegativa() {
        assertThrows(IllegalArgumentException.class, ()
            -> new ColaboradorProducao("Pedro Bueno", 123, 1500, -100, 10));
    }

    @Test
    @DisplayName ("CT012 – Valor por Unidade Produzida Negativo")
    public void testarValorPorUnidadeProduzidaNegativa() {
        assertThrows(IllegalArgumentException.class, ()
            -> new ColaboradorProducao("Pedro Bueno", 123, 1500, 100, -10));
    }

    @Test
    @DisplayName ("CT017 – Cálculo de Salário por Produção")
    public void testarCalcularSalarioFinalProducao() {
        assertEquals(2500, colaboradorProducao.calcularSalarioFinal(), 0.001);
    }
}
