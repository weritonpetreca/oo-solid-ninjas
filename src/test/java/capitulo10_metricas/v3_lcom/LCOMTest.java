package capitulo10_metricas.v3_lcom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap10 v3 — LCOM: Coesão (Witcher)")
class LCOMTest {

    @Nested
    @DisplayName("FinanceiroDeGuilda — LCOM ≈ 0")
    class FinanceiroTest {

        @Test
        @DisplayName("deve calcular líquida descontando a taxa")
        void deveCalcularLiquida() {
            var fin = new FinanceiroDeGuilda(1000.0, 0.15);
            assertEquals(850.0, fin.calcularLiquida(), 0.001);
        }

        @Test
        @DisplayName("deve calcular imposto como percentual da recompensa")
        void deveCalcularImposto() {
            var fin = new FinanceiroDeGuilda(1000.0, 0.15);
            assertEquals(150.0, fin.calcularImposto(), 0.001);
        }

        @Test
        @DisplayName("deve identificar recompensa premium acima de 5000")
        void deveIdentificarPremium() {
            var finPremium  = new FinanceiroDeGuilda(6000.0, 0.15);
            var finNormal   = new FinanceiroDeGuilda(1000.0, 0.15);
            assertTrue(finPremium.isPremium());
            assertFalse(finNormal.isPremium());
        }
    }

    @Nested
    @DisplayName("ReputacaoDeCacador — LCOM ≈ 0")
    class ReputacaoTest {

        @Test
        @DisplayName("deve iniciar como NOVATO")
        void deveIniciarComoNovato() {
            var rep = new ReputacaoDeCacador("Geralt");
            assertEquals("NOVATO", rep.getRanking());
            assertEquals(0, rep.getTotalMissoes());
        }

        @Test
        @DisplayName("deve promover para EXPERIENTE após 5 missões")
        void devePromoverParaExperiente() {
            var rep = new ReputacaoDeCacador("Geralt");
            for (int i = 0; i < 5; i++) rep.registrarMissao();
            assertEquals("EXPERIENTE", rep.getRanking());
        }

        @Test
        @DisplayName("deve promover para VETERANO após 10 missões")
        void devePromoverParaVeterano() {
            var rep = new ReputacaoDeCacador("Lambert");
            for (int i = 0; i < 10; i++) rep.registrarMissao();
            assertEquals("VETERANO", rep.getRanking());
        }

        @Test
        @DisplayName("deve promover para MESTRE após 25 missões")
        void devePromoverParaMestre() {
            var rep = new ReputacaoDeCacador("Vesemir");
            for (int i = 0; i < 25; i++) rep.registrarMissao();
            assertEquals("MESTRE", rep.getRanking());
        }

        @Test
        @DisplayName("gerarInsignia deve conter ranking e nome")
        void gerarInsigniaDeveConterRankingENome() {
            var rep = new ReputacaoDeCacador("Ciri");
            String insignia = rep.gerarInsignia();
            assertTrue(insignia.contains("Ciri"));
            assertTrue(insignia.contains("NOVATO"));
        }
    }

    @Nested
    @DisplayName("CalculadorLCOM — cálculo da métrica")
    class CalculadorLCOMTest {

        private final CalculadorLCOM calc = new CalculadorLCOM();

        @Test
        @DisplayName("LCOM = 0 quando todos os métodos acessam todos os atributos")
        void lcomZeroQuandoCoeso() {
            // M=3 métodos, F=2 atributos, MF=6 (cada método acessa ambos atributos)
            double lcom = calc.calcularLcomHS(3, 2, 6);
            assertEquals(0.0, lcom, 0.001);
        }

        @Test
        @DisplayName("LCOM próximo de 1 para classe não coesa")
        void lcomAltoParaClasseNaoCoesa() {
            // M=4, F=4, MF=4 (cada método acessa apenas 1 atributo)
            double lcom = calc.calcularLcomHS(4, 4, 4);
            assertEquals(0.75, lcom, 0.01);
        }

        @Test
        @DisplayName("LCOM = 0 quando M ou F é zero")
        void lcomZeroQuandoSemMetodosOuAtributos() {
            assertEquals(0.0, calc.calcularLcomHS(0, 5, 10), 0.001);
            assertEquals(0.0, calc.calcularLcomHS(5, 0, 10), 0.001);
        }

        @Test
        @DisplayName("avaliação correta por faixas de LCOM")
        void avaliacaoCorretaPorFaixas() {
            assertTrue(calc.avaliar(0.1).contains("Alta coesão"));
            assertTrue(calc.avaliar(0.4).contains("aceitável"));
            assertTrue(calc.avaliar(0.65).contains("Considere dividir"));
            assertTrue(calc.avaliar(0.9).contains("divida"));
        }
    }
}