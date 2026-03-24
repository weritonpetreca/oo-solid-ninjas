package capitulo10_metricas.v4_acoplamento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap10 v4 — Acoplamento Aferente e Eferente (Witcher)")
class AcoplamentoTest {

    @Nested
    @DisplayName("ContratoDeCaca — CA alto, CE = 0")
    class ContratoDeCacaTest {

        @Test
        @DisplayName("deve criar contrato com atributos corretos")
        void deveCriarContrato() {
            var c = new ContratoDeCaca("Grifo", 3000.0, "Velen");
            assertEquals("Grifo",   c.getMonstro());
            assertEquals(3000.0,    c.getValorBase(), 0.001);
            assertEquals("Velen",   c.getRegiao());
        }
    }

    @Nested
    @DisplayName("TaxaDaGuilda — CA alto, CE = 0")
    class TaxaDaGuildaTest {

        private final TaxaDaGuilda taxa = new TaxaDaGuilda();

        @Test
        @DisplayName("deve calcular 15% de taxa sobre o valor bruto")
        void deveCalcularQuinzePorcento() {
            assertEquals(300.0, taxa.calcular(2000.0), 0.001);
        }

        @Test
        @DisplayName("deve calcular valor líquido corretamente")
        void deveCalcularLiquida() {
            assertEquals(1700.0, taxa.calcularLiquida(2000.0), 0.001);
        }
    }

    @Nested
    @DisplayName("GerenciadorLimpo — CE com dependências estáveis (interfaces)")
    class GerenciadorLimpoTest {

        @Test
        @DisplayName("deve processar contrato via interfaces sem exceção")
        void deveProcessarContratoViaInterfaces() {
            // Arrange: implementações fake via lambdas (interfaces funcionais)
            List<String> salvos      = new ArrayList<>();
            List<String> notificados = new ArrayList<>();
            List<String> despachados = new ArrayList<>();
            List<String> integrados  = new ArrayList<>();
            List<String> logados     = new ArrayList<>();

            Repositorio       repo    = c -> salvos.add(c.getMonstro());
            Notificador       notif   = (d, m) -> notificados.add(d);
            Despachador       desp    = (dest, c) -> despachados.add(dest);
            IntegracaoExterna integ   = c -> integrados.add(c.getMonstro());
            Log               log     = m -> logados.add(m);

            var gerenciador = new GerenciadorLimpo(repo, notif, desp, integ, log);
            var contrato    = new ContratoDeCaca("Basilisco", 5000.0, "Skellige");
            var taxa        = new TaxaDaGuilda();

            // Act
            assertDoesNotThrow(() -> gerenciador.processarContrato(contrato, taxa));

            // Assert: todas as interfaces foram invocadas
            assertEquals(1, salvos.size(),      "Repositorio deve ter sido chamado");
            assertEquals(1, notificados.size(), "Notificador deve ter sido chamado");
            assertEquals(1, despachados.size(), "Despachador deve ter sido chamado");
            assertEquals(1, integrados.size(),  "Integracao deve ter sido chamada");
            assertEquals(1, logados.size(),     "Log deve ter sido chamado");
        }

        @Test
        @DisplayName("deve salvar o contrato com o monstro correto")
        void deveSalvarContratoComMonstroCorreto() {
            List<String> salvos = new ArrayList<>();
            var gerenciador = new GerenciadorLimpo(
                    c -> salvos.add(c.getMonstro()),
                    (d, m) -> {},
                    (d, c) -> {},
                    c -> {},
                    m -> {}
            );
            gerenciador.processarContrato(
                    new ContratoDeCaca("Grifo Real", 3000.0, "Velen"),
                    new TaxaDaGuilda()
            );
            assertEquals("Grifo Real", salvos.get(0));
        }
    }

    @Nested
    @DisplayName("RelatorioAcoplamento — avaliações por CA e CE")
    class RelatorioTest {

        @Test
        @DisplayName("CA = 0 deve sugerir remoção")
        void ca0DeveSugerirRemocao() {
            var r = new RelatorioAcoplamento("Classe", 0, 0);
            assertTrue(r.avaliarCA().contains("remoção"));
        }

        @Test
        @DisplayName("CA alto deve indicar estabilidade")
        void caAltoDeveIndicarEstabilidade() {
            var r = new RelatorioAcoplamento("Classe", 12, 0);
            assertTrue(r.avaliarCA().contains("estável") || r.avaliarCA().contains("Muito estável"));
        }

        @Test
        @DisplayName("CE = 0 deve indicar independência")
        void ce0DeveIndicarIndependencia() {
            var r = new RelatorioAcoplamento("Classe", 5, 0);
            assertTrue(r.avaliarCE().contains("Independente"));
        }

        @Test
        @DisplayName("CE muito alto deve indicar fragilidade")
        void ceMuitoAltoDeveIndicarFragilidade() {
            var r = new RelatorioAcoplamento("Classe", 2, 15);
            assertTrue(r.avaliarCE().contains("muito alto"));
        }
    }
}