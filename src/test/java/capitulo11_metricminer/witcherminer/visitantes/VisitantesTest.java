package capitulo11_metricminer.witcherminer.visitantes;

import capitulo11_metricminer.witcherminer.adapters.CSVGuilda;
import capitulo11_metricminer.witcherminer.adapters.RegistroHistoricoFactory;
import capitulo11_metricminer.witcherminer.domain.ContratoRegistrado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Visitantes: Testes das Regras de Análise")
class VisitantesTest {

    @Test
    @DisplayName("ContadorDeMissoesCompletas deve contar corretamente")
    void deveContarMissoesCompletas() {
        var repo = RegistroHistoricoFactory.criarGuilda("Teste");
        ContratoRegistrado contrato = repo.getArquivo().getContrato("CTR-001");
        var csv = new CSVGuilda("teste.csv");
        
        var visitante = new ContadorDeMissoesCompletas();
        visitante.processar(repo, contrato, csv);
        
        // CTR-001 tem 1 missão concluída
        assertTrue(csv.getConteudo().contains("1"));
        assertEquals(1, csv.getLinhas());
    }

    @Test
    @DisplayName("CalculadoraDeRecompensas deve calcular bruto e líquido")
    void deveCalcularRecompensas() {
        var repo = RegistroHistoricoFactory.criarGuilda("Teste");
        ContratoRegistrado contrato = repo.getArquivo().getContrato("CTR-001");
        var csv = new CSVGuilda("teste.csv");

        var visitante = new CalculadoraDeRecompensas();
        visitante.processar(repo, contrato, csv);
        
        // Bruto 3000.00, Taxa 450.00, Líquido 2550.00
        assertTrue(csv.getConteudo().contains("3000.00"));
        assertTrue(csv.getConteudo().contains("2550.00"));
    }

    @Test
    @DisplayName("AnalisadorDeCancelamentos deve alertar quando limite é atingido")
    void deveAnalisarCancelamentos() {
        var repo = RegistroHistoricoFactory.criarGuilda("Teste");
        ContratoRegistrado contrato = repo.getArquivo().getContrato("CTR-002");
        var csv = new CSVGuilda("teste.csv");

        // Limite 1, contrato tem 1 cancelamento
        var visitante = new AnalisadorDeCancelamentos(1);
        visitante.processar(repo, contrato, csv);
        
        assertEquals(1, csv.getLinhas());
        assertTrue(csv.getConteudo().contains("ALERTA"));
    }
}
