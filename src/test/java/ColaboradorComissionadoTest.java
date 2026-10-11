import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.folhaPagamento.model.ColaboradorComissionado;

public class ColaboradorComissionadoTest {
    private ColaboradorComissionado colaboradorComissionado;
    
    @BeforeEach //Roda antes dos testes iniciarem
    public void criarColaboradorComissionado() {
        colaboradorComissionado = new ColaboradorComissionado("Pedro Bueno", 123, 1500, 100, 10);
    }

    @Test
    @DisplayName("CT009 – Valor de Vendas Negativo")
    public void testarValorDeVendasNegativa() {
        assertThrows(IllegalArgumentException.class, ()
            -> new ColaboradorComissionado("Pedro Bueno", 123, 1500, -100, 10));
    }

    @Test
    @DisplayName("CT010 – Comissão Negativa")
    public void testarComissaoNegativa() {
        assertThrows(IllegalArgumentException.class, ()
            -> new ColaboradorComissionado("Pedro Bueno", 123, 1500, 100, -10));
    }

    @Test
    @DisplayName("CT016 – Cálculo de Salário Comissionado")
    public void testarCalcularSalarioFinalComissionado() {
        assertEquals(1510, colaboradorComissionado.calcularSalarioFinal(), 0.001);
    }
}