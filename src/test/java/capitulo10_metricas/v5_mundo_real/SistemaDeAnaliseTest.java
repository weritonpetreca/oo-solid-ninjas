package capitulo10_metricas.v5_mundo_real;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap10 v5 — Sistema de Análise de Qualidade (Witcher)")
class SistemaDeAnaliseTest {

    @Nested
    @DisplayName("AvaliadorDeClasse — validação contra limites")
    class AvaliadorTest {

        private final LimitesDeQualidade limites    = new LimitesDeQualidade();
        private final AvaliadorDeClasse  avaliador  = new AvaliadorDeClasse(limites);

        @Test
        @DisplayName("classe com todas as métricas saudáveis não deve ter violações")
        void classeSaudavelNaoDeveViolar() {
            var analise = new AnaliseDeClasse("ClasseCoesa", 3, 2, 3, 2, 0.1, 2, 5);
            assertTrue(avaliador.avaliar(analise).isEmpty());
            assertTrue(avaliador.estaEmConformidade(analise));
        }

        @Test
        @DisplayName("classe com CC acima do limite deve reportar violação")
        void ccAcimaDoLimiteDeveReportarViolacao() {
            var analise = new AnaliseDeClasse("MetodoComplexo", 15, 2, 3, 2, 0.1, 2, 5);
            var violacoes = avaliador.avaliar(analise);
            assertFalse(violacoes.isEmpty());
            assertTrue(violacoes.stream().anyMatch(v -> v.contains("CC")));
        }

        @Test
        @DisplayName("classe com NOA acima do limite deve reportar violação")
        void noaAcimaDoLimiteDeveReportarViolacao() {
            var analise = new AnaliseDeClasse("GodClass", 3, 10, 5, 2, 0.1, 2, 5);
            var violacoes = avaliador.avaliar(analise);
            assertTrue(violacoes.stream().anyMatch(v -> v.contains("NOA")));
        }

        @Test
        @DisplayName("classe com LCOM acima do limite deve sugerir divisão")
        void lcomAcimaDoLimiteDeveSugerirDivisao() {
            var analise = new AnaliseDeClasse("ClasseNaoCoesa", 3, 4, 5, 2, 0.85, 2, 5);
            var violacoes = avaliador.avaliar(analise);
            assertTrue(violacoes.stream().anyMatch(v -> v.contains("LCOM")));
        }

        @Test
        @DisplayName("classe com CE acima do limite deve indicar fragilidade")
        void ceAcimaDoLimiteDeveIndicarFragilidade() {
            var analise = new AnaliseDeClasse("ClasseFragil", 3, 2, 3, 2, 0.1, 15, 5);
            var violacoes = avaliador.avaliar(analise);
            assertTrue(violacoes.stream().anyMatch(v -> v.contains("CE")));
        }

        @Test
        @DisplayName("múltiplas violações devem ser todas reportadas")
        void multipasViolacoesDevemSerTodasReportadas() {
            // Viola CC (12 > 10), NOA (9 > 7) e CE (12 > 10)
            var analise = new AnaliseDeClasse("Catástrofe", 12, 9, 5, 3, 0.3, 12, 1);
            var violacoes = avaliador.avaliar(analise);
            assertTrue(violacoes.size() >= 3);
        }
    }

    @Nested
    @DisplayName("LimitesDeQualidade — configuração customizada")
    class LimitesTest {

        @Test
        @DisplayName("limites padrão devem ter valores razoáveis")
        void limitesDefault() {
            var l = new LimitesDeQualidade();
            assertTrue(l.getMaxCC()   > 0);
            assertTrue(l.getMaxNOA()  > 0);
            assertTrue(l.getMaxNOM()  > 0);
            assertTrue(l.getMaxNOP()  > 0);
            assertTrue(l.getMaxLCOM() > 0 && l.getMaxLCOM() <= 1);
            assertTrue(l.getMaxCE()   > 0);
        }

        @Test
        @DisplayName("limites customizados devem ser respeitados")
        void limitesCustomizados() {
            var limites   = new LimitesDeQualidade(5, 3, 8, 3, 0.5, 5);
            var avaliador = new AvaliadorDeClasse(limites);

            // CC = 6 viola o limite customizado (5)
            var analise = new AnaliseDeClasse("Classe", 6, 2, 4, 2, 0.3, 3, 2);
            assertFalse(avaliador.estaEmConformidade(analise));
        }
    }

    @Nested
    @DisplayName("RelatorioDeQualidade — visão do sistema como um todo")
    class RelatorioTest {

        @Test
        @DisplayName("deve identificar classes em conformidade")
        void deveIdentificarClassesEmConformidade() {
            var limites    = new LimitesDeQualidade();
            var avaliador  = new AvaliadorDeClasse(limites);
            var classeOK   = new AnaliseDeClasse("ClasseOK", 2, 2, 2, 2, 0.1, 2, 5);
            var classeRuim = new AnaliseDeClasse("ClasseRuim", 15, 9, 25, 8, 0.9, 20, 1);

            assertTrue(avaliador.estaEmConformidade(classeOK));
            assertFalse(avaliador.estaEmConformidade(classeRuim));
        }

        @Test
        @DisplayName("relatório não deve lançar exceção para lista de classes")
        void relatorioNaoDeveLancarExcecao() {
            var classes = List.of(
                    new AnaliseDeClasse("A", 2, 2, 2, 2, 0.1, 2, 5),
                    new AnaliseDeClasse("B", 15, 9, 25, 8, 0.9, 20, 1)
            );
            var relatorio = new RelatorioDeQualidade(new LimitesDeQualidade());
            assertDoesNotThrow(() -> relatorio.imprimir(classes));
        }
    }
}