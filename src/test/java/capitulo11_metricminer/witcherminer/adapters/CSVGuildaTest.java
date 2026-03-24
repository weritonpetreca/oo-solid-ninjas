package capitulo11_metricminer.witcherminer.adapters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Adapter: Teste do Mecanismo de Persistência CSV")
class CSVGuildaTest {

    @Test
    @DisplayName("Deve escrever múltiplas linhas e contar corretamente")
    void deveEscreverMultiplasLinhas() {
        var csv = new CSVGuilda("teste.csv");
        
        csv.escrever("col1", "col2", "col3");
        csv.escrever(1, 2, 3);
        
        assertEquals(2, csv.getLinhas());
        String expected = "col1,col2,col3\n1,2,3\n";
        assertEquals(expected, csv.getConteudo());
    }

    @Test
    @DisplayName("Deve iniciar com zero linhas")
    void deveIniciarVazio() {
        var csv = new CSVGuilda("teste.csv");
        assertEquals(0, csv.getLinhas());
        assertTrue(csv.getConteudo().isEmpty());
    }
}
