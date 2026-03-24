package capitulo11_metricminer.witcherminer.metricas;

import capitulo11_metricminer.witcherminer.domain.AcaoDoCacador;
import capitulo11_metricminer.witcherminer.domain.TipoDeAcao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Métricas: Testes dos Cálculos de Métricas")
class MetricasTest {

    @Nested
    @DisplayName("Taxa de Sucesso")
    class TaxaDeSucesso {
        private final TaxaDeSucessoMetrica metrica = new TaxaDeSucessoMetrica();

        @Test
        @DisplayName("Deve retornar 0.5 para 1 de 2 missões concluídas")
        void deveCalcularTaxaDeSucesso() {
            List<AcaoDoCacador> acoes = List.of(
                new AcaoDoCacador("a", TipoDeAcao.MISSAO_NOVA, 0, ""),
                new AcaoDoCacador("b", TipoDeAcao.MISSAO_NOVA, 0, ""),
                new AcaoDoCacador("c", TipoDeAcao.MISSAO_CONCLUIDA, 0, "")
            );
            assertEquals(0.5, metrica.calcular(acoes), 0.001);
        }

        @Test
        @DisplayName("Deve retornar 0.0 se não houver missões tentadas")
        void deveRetornarZeroSeNaoHouverTentativas() {
            List<AcaoDoCacador> acoes = List.of(
                new AcaoDoCacador("c", TipoDeAcao.MISSAO_CONCLUIDA, 0, "")
            );
            assertEquals(0.0, metrica.calcular(acoes), 0.001);
        }

        @Test
        @DisplayName("Deve retornar 0.0 para lista de ações vazia")
        void deveRetornarZeroParaListaVazia() {
            assertEquals(0.0, metrica.calcular(Collections.emptyList()), 0.001);
        }
    }

    @Nested
    @DisplayName("Recompensa Média")
    class RecompensaMedia {
        private final RecompensaMediaMetrica metrica = new RecompensaMediaMetrica();

        @Test
        @DisplayName("Deve calcular a recompensa média corretamente")
        void deveCalcularRecompensaMedia() {
            List<AcaoDoCacador> acoes = List.of(
                new AcaoDoCacador("a", TipoDeAcao.MISSAO_CONCLUIDA, 0, ""),
                new AcaoDoCacador("b", TipoDeAcao.RECOMPENSA_PAGA, 1000, ""),
                new AcaoDoCacador("c", TipoDeAcao.MISSAO_CONCLUIDA, 0, ""),
                new AcaoDoCacador("d", TipoDeAcao.RECOMPENSA_PAGA, 500, "")
            );
            // 1500 de recompensa / 2 missões concluídas = 750
            assertEquals(750.0, metrica.calcular(acoes), 0.001);
        }
    }
    
    @Nested
    @DisplayName("Índice de Cancelamento")
    class IndiceDeCancelamento {
        private final IndiceDeCancelamentoMetrica metrica = new IndiceDeCancelamentoMetrica();

        @Test
        @DisplayName("Deve calcular o índice de cancelamento")
        void deveCalcularIndiceCancelamento() {
            List<AcaoDoCacador> acoes = List.of(
                new AcaoDoCacador("a", TipoDeAcao.MISSAO_NOVA, 0, ""),
                new AcaoDoCacador("b", TipoDeAcao.MISSAO_CANCELADA, 0, ""),
                new AcaoDoCacador("c", TipoDeAcao.MISSAO_CONCLUIDA, 0, ""),
                new AcaoDoCacador("d", TipoDeAcao.RECOMPENSA_PAGA, 0, "")
            );
            // 1 cancelada / 4 ações totais = 0.25
            assertEquals(0.25, metrica.calcular(acoes), 0.001);
        }
    }
}
