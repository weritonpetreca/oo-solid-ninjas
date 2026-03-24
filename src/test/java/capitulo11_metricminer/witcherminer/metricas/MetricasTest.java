package capitulo11_metricminer.witcherminer.metricas;

import capitulo11_metricminer.witcherminer.domain.AcaoDoCacador;
import capitulo11_metricminer.witcherminer.domain.TipoDeAcao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Métricas: Testes dos Cálculos de Métricas")
class MetricasTest {

    @Test
    @DisplayName("TaxaDeSucessoMetrica deve calcular corretamente")
    void deveCalcularTaxaDeSucesso() {
        var metrica = new TaxaDeSucessoMetrica();
        List<AcaoDoCacador> acoes = List.of(
            new AcaoDoCacador("a", TipoDeAcao.MISSAO_NOVA, 0, ""),
            new AcaoDoCacador("b", TipoDeAcao.MISSAO_NOVA, 0, ""),
            new AcaoDoCacador("c", TipoDeAcao.MISSAO_CONCLUIDA, 0, "")
        );
        // 1 concluída / 2 tentadas = 0.5
        assertEquals(0.5, metrica.calcular(acoes), 0.001);
    }

    // Você pode adicionar testes para as outras métricas aqui...
}
