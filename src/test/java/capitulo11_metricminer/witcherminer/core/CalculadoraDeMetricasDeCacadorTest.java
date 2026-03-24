package capitulo11_metricminer.witcherminer.core;

import capitulo11_metricminer.witcherminer.adapters.CSVGuilda;
import capitulo11_metricminer.witcherminer.domain.ContratoRegistrado;
import capitulo11_metricminer.witcherminer.domain.RegistroHistoricoGuilda;
import capitulo11_metricminer.witcherminer.ports.FabricaDeMetrica;
import capitulo11_metricminer.witcherminer.ports.MecanismoDePersistencia;
import capitulo11_metricminer.witcherminer.ports.MetricaDeCacador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Core: Teste do Visitante CalculadoraDeMetricas")
class CalculadoraDeMetricasDeCacadorTest {

    @Mock private FabricaDeMetrica fabricaMock;
    @Mock private MetricaDeCacador metricaMock;
    @Mock private MecanismoDePersistencia persistenciaMock;
    @Mock private RegistroHistoricoGuilda repoMock;
    @Mock private ContratoRegistrado contratoMock;

    @Test
    @DisplayName("Deve construir a métrica, calcular e persistir o resultado formatado")
    void deveOrquestrarCalculoEMetrica() {
        // ARRANGE
        // Ensinamos os mocks a se comportarem
        when(fabricaMock.construir()).thenReturn(metricaMock);
        when(fabricaMock.getNome()).thenReturn("METRICA_TESTE");
        when(metricaMock.calcular(any())).thenReturn(0.85714);
        when(contratoMock.getAcoes()).thenReturn(List.of());
        when(contratoMock.getId()).thenReturn("CTR-TESTE");
        when(contratoMock.getCacador()).thenReturn("Tester de Rívia");
        when(repoMock.getNome()).thenReturn("Repo de Teste");

        // O visitante que estamos testando
        var visitante = new CalculadoraDeMetricasDeCacador(fabricaMock);

        // ACT
        visitante.processar(repoMock, contratoMock, persistenciaMock);

        // ASSERT
        // Verificamos se a fábrica foi chamada para criar a métrica
        verify(fabricaMock).construir();
        // Verificamos se a métrica foi chamada para calcular
        verify(metricaMock).calcular(any());

        // A MÁGICA: Capturamos o que foi enviado para o método de escrita
        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(persistenciaMock).escrever(captor.capture(), captor.capture(), captor.capture(), captor.capture(), captor.capture());

        List<Object> valoresEscritos = captor.getAllValues();
        assertEquals("Repo de Teste", valoresEscritos.get(0));
        assertEquals("CTR-TESTE", valoresEscritos.get(1));
        assertEquals("Tester de Rívia", valoresEscritos.get(2));
        assertEquals("METRICA_TESTE", valoresEscritos.get(3));
        assertEquals("0,8571", valoresEscritos.get(4)); // Verifica a formatação!
    }
}
