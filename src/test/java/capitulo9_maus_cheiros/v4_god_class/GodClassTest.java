package capitulo9_maus_cheiros.v4_god_class;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap9 v4 — God Class: serviços distribuídos")
class GodClassTest {

    @Nested
    @DisplayName("RegistroDeCacadores")
    class RegistroDeCacadoresTest {

        private RegistroDeCacadores registro;

        @BeforeEach
        void setUp() { registro = new RegistroDeCacadores(); }

        @Test
        @DisplayName("deve registrar e confirmar existência")
        void deveRegistrarEConfirmarExistencia() {
            registro.registrar("Geralt");
            assertTrue(registro.existe("Geralt"));
        }

        @Test
        @DisplayName("deve retornar false para caçador não registrado")
        void deveRetornarFalseParaNaoRegistrado() {
            assertFalse(registro.existe("Vesemir"));
        }

        @Test
        @DisplayName("total deve refletir o número de registros")
        void totalDeveRefletirNumeroDeRegistros() {
            registro.registrar("Geralt");
            registro.registrar("Lambert");
            assertEquals(2, registro.total());
        }
    }

    @Nested
    @DisplayName("CalculadorDeImpostoGuilda")
    class CalculadorDeImpostoGuildaTest {

        private final CalculadorDeImpostoGuilda calc = new CalculadorDeImpostoGuilda();

        @Test
        @DisplayName("deve calcular 15% do valor")
        void deveCalcularQuinzePorCento() {
            assertEquals(300.0, calc.calcular(2000.0), 0.001);
        }

        @Test
        @DisplayName("deve retornar zero para valor zero")
        void deveRetornarZeroParaValorZero() {
            assertEquals(0.0, calc.calcular(0.0), 0.001);
        }
    }

    @Nested
    @DisplayName("TesouroDaGuilda")
    class TesouroDaGuildaTest {

        @Test
        @DisplayName("deve descontar pagamento do saldo")
        void deveDescontarPagamentoDoSaldo() {
            var tesouro = new TesouroDaGuilda(10_000.0);
            tesouro.pagar("Geralt", 2000.0);
            assertEquals(8_000.0, tesouro.getSaldo(), 0.001);
        }

        @Test
        @DisplayName("múltiplos pagamentos devem acumular descontos")
        void multiplosPagamentosDevemAcumular() {
            var tesouro = new TesouroDaGuilda(10_000.0);
            tesouro.pagar("Geralt",  3000.0);
            tesouro.pagar("Lambert", 1000.0);
            assertEquals(6_000.0, tesouro.getSaldo(), 0.001);
        }
    }

    @Nested
    @DisplayName("GuildaCoordenadora — integração dos serviços")
    class GuildaCoordenadoraTest {

        private GuildaCoordenadora guilda;
        private TesouroDaGuilda  tesouro;

        @BeforeEach
        void setUp() {
            var registro   = new RegistroDeCacadores();
            var contratos  = new GestorDeContratos();
            var calc       = new CalculadorDeImpostoGuilda();
            tesouro        = new TesouroDaGuilda(50_000.0);
            var notificacao = new ServicoDeNotificacao();

            guilda = new GuildaCoordenadora(registro, contratos, calc, tesouro, notificacao);
        }

        @Test
        @DisplayName("concluirMissao deve deduzir imposto do tesouro")
        void concluirMissaoDeveDeduzirImposto() {
            guilda.concluirMissao("Geralt", "Grifo", 2000.0);
            // Paga líquido = 2000 - 300 = 1700
            assertEquals(50_000.0 - 1700.0, tesouro.getSaldo(), 0.001);
        }
    }
}
