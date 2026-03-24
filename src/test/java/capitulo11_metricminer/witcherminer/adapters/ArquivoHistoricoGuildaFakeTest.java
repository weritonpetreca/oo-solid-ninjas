package capitulo11_metricminer.witcherminer.adapters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Adapter: Teste do Repositório Fake de Contratos")
class ArquivoHistoricoGuildaFakeTest {

    @Test
    @DisplayName("Deve retornar a lista de referências de contrato")
    void deveRetornarReferencias() {
        var repo = new ArquivoHistoricoGuildaFake("Teste");
        var refs = repo.getReferencias();
        assertEquals(3, refs.size());
        assertEquals("CTR-001", refs.get(0).getId());
    }

    @Test
    @DisplayName("Deve retornar um contrato específico pelo ID")
    void deveRetornarContratoPorId() {
        var repo = new ArquivoHistoricoGuildaFake("Teste");
        var contrato = repo.getContrato("CTR-001");
        assertEquals("Geralt de Rívia", contrato.getCacador());
        assertEquals(3, contrato.getAcoes().size());
    }
}
