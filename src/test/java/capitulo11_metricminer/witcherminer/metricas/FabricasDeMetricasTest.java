package capitulo11_metricminer.witcherminer.metricas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Métricas: Testes das Fábricas de Métricas")
class FabricasDeMetricasTest {

    @Test
    @DisplayName("FabricaTaxaDeSucesso deve construir a métrica e nome corretos")
    void deveConstruirTaxaDeSucesso() {
        var fabrica = new FabricaTaxaDeSucesso();
        var metrica = fabrica.construir();
        
        assertInstanceOf(TaxaDeSucessoMetrica.class, metrica);
        assertEquals("TAXA_SUCESSO", fabrica.getNome());
    }

    @Test
    @DisplayName("FabricaRecompensaMedia deve construir a métrica e nome corretos")
    void deveConstruirRecompensaMedia() {
        var fabrica = new FabricaRecompensaMedia();
        var metrica = fabrica.construir();

        assertInstanceOf(RecompensaMediaMetrica.class, metrica);
        assertEquals("RECOMPENSA_MEDIA", fabrica.getNome());
    }

    @Test
    @DisplayName("FabricaIndiceDeCancelamento deve construir a métrica e nome corretos")
    void deveConstruirIndiceDeCancelamento() {
        var fabrica = new FabricaIndiceDeCancelamento();
        var metrica = fabrica.construir();

        assertInstanceOf(IndiceDeCancelamentoMetrica.class, metrica);
        assertEquals("INDICE_CANCELAMENTO", fabrica.getNome());
    }
}
