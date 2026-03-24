package capitulo11_metricminer.witcherminer.metricas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Métricas: Testes das Fábricas de Métricas")
class FabricasDeMetricasTest {

    @Test
    @DisplayName("FabricaTaxaDeSucesso deve construir a métrica correta")
    void deveConstruirTaxaDeSucesso() {
        var fabrica = new FabricaTaxaDeSucesso();
        var metrica = fabrica.construir();
        
        assertInstanceOf(TaxaDeSucessoMetrica.class, metrica);
        assertEquals("TAXA_SUCESSO", fabrica.getNome());
    }

    // Adicione aqui testes para as outras fábricas que você criar,
    // como FabricaRecompensaMedia, etc.
}
