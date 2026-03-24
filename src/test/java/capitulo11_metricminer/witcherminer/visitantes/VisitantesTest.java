package capitulo11_metricminer.witcherminer.visitantes;

import capitulo11_metricminer.witcherminer.adapters.CSVGuilda;
import capitulo11_metricminer.witcherminer.adapters.RegistroHistoricoFactory;
import capitulo11_metricminer.witcherminer.domain.ContratoRegistrado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Visitantes: Testes das Regras de Análise")
class VisitantesTest {

    private capitulo11_metricminer.witcherminer.domain.RegistroHistoricoGuilda repo;

    @BeforeEach
    void setUp() {
        repo = RegistroHistoricoFactory.criarGuilda("Teste");
    }

    @Nested
    @DisplayName("Contador de Missões Concluídas")
    class ContadorDeMissoesTest {
        @Test
        @DisplayName("Deve contar 1 para contrato com uma conclusão")
        void deveContarUmaMissao() {
            ContratoRegistrado contrato = repo.getArquivo().getContrato("CTR-001");
            var csv = new CSVGuilda("teste.csv");
            var visitante = new ContadorDeMissoesCompletas();
            
            visitante.processar(repo, contrato, csv);
            
            assertTrue(csv.getConteudo().contains("1"));
        }

        @Test
        @DisplayName("Deve contar 0 para contrato sem conclusões")
        void deveContarZeroMissoes() {
            ContratoRegistrado contrato = repo.getArquivo().getContrato("CTR-002");
            var csv = new CSVGuilda("teste.csv");
            var visitante = new ContadorDeMissoesCompletas();

            visitante.processar(repo, contrato, csv);
            
            assertTrue(csv.getConteudo().contains("0"));
        }
    }

    @Nested
    @DisplayName("Calculadora de Recompensas")
    class CalculadoraDeRecompensasTest {
        @Test
        @DisplayName("Deve calcular bruto e líquido corretamente")
        void deveCalcularRecompensas() {
            ContratoRegistrado contrato = repo.getArquivo().getContrato("CTR-001");
            var csv = new CSVGuilda("teste.csv");
            var visitante = new CalculadoraDeRecompensas();

            visitante.processar(repo, contrato, csv);
            
            assertTrue(csv.getConteudo().contains("3000.00")); // Bruto
            assertTrue(csv.getConteudo().contains("2550.00")); // Líquido
        }
    }

    @Nested
    @DisplayName("Analisador de Cancelamentos")
    class AnalisadorDeCancelamentosTest {
        @Test
        @DisplayName("Deve gerar alerta se o limite for atingido")
        void deveGerarAlerta() {
            ContratoRegistrado contrato = repo.getArquivo().getContrato("CTR-002");
            var csv = new CSVGuilda("teste.csv");
            var visitante = new AnalisadorDeCancelamentos(1); // Limite 1

            visitante.processar(repo, contrato, csv);
            
            assertEquals(1, csv.getLinhas());
            assertTrue(csv.getConteudo().contains("ALERTA"));
        }

        @Test
        @DisplayName("Não deve gerar alerta se o limite não for atingido")
        void naoDeveGerarAlerta() {
            ContratoRegistrado contrato = repo.getArquivo().getContrato("CTR-002");
            var csv = new CSVGuilda("teste.csv");
            var visitante = new AnalisadorDeCancelamentos(2); // Limite 2

            visitante.processar(repo, contrato, csv);
            
            assertEquals(0, csv.getLinhas());
        }
    }
}
