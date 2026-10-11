import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.folhaPagamento.model.ColaboradorPadrao;

public class ColaboradorPadraoTest {
    private ColaboradorPadrao colaborador;

    @BeforeEach //Roda antes dos testes iniciarem
    public void criarColaboradorPadrao() {
        colaborador = new ColaboradorPadrao("Pedro Bueno", 123, 1500);
    }

    @Test
    @DisplayName("CT005 – Nome Vazio")
    public void testarNomeVazio() {
        assertThrows(IllegalArgumentException.class, () 
            -> new ColaboradorPadrao("", 123, 1500));
    }

    @Test
    @DisplayName ("CT007 – Salário Negativo")
    public void testarSalarioNegativo() {
        assertThrows(IllegalArgumentException.class, ()
            -> new ColaboradorPadrao("Pedro Bueno", 123, -1500));
    }

    @Test
    @DisplayName ("CT015 – Cálculo de Salário Padrão")
    public void testarCalcularSalarioFinalPadrao() {
        assertEquals(1500, colaborador.calcularSalarioFinal(), 0.001);
    }
}
