package capitulo10_metricas.v2_tamanho;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap10 v2 — Tamanho de Métodos e Classes (Witcher)")
class TamanhoTest {

    @Nested
    @DisplayName("IdentidadeDeCacador — NOA=3, coeso")
    class IdentidadeTest {

        @Test
        @DisplayName("deve criar identidade com nome, escola e credencial")
        void deveCriarIdentidade() {
            var id = new IdentidadeDeCacador("Geralt de Rívia", "Escola do Lobo", "G01-LOBO");
            assertEquals("Geralt de Rívia", id.getNome());
        }

        @Test
        @DisplayName("toString deve conter nome e credencial")
        void toStringDeveConterNomeECredencial() {
            var id = new IdentidadeDeCacador("Geralt", "Lobo", "G01-LOBO");
            assertTrue(id.toString().contains("Geralt"));
            assertTrue(id.toString().contains("G01-LOBO"));
        }
    }

    @Nested
    @DisplayName("CarteiraDeRecompensas — NOA=2, métodos pequenos")
    class CarteiraTest {

        @Test
        @DisplayName("deve acumular recompensas registradas")
        void deveAcumularRecompensas() {
            var carteira = new CarteiraDeRecompensas();
            carteira.registrar(1000.0);
            carteira.registrar(2000.0);
            assertEquals(3000.0, carteira.getAcumulado(), 0.001);
            assertEquals(2, carteira.getMissoes());
        }

        @Test
        @DisplayName("deve iniciar com acumulado zero e zero missões")
        void deveIniciarZerado() {
            var carteira = new CarteiraDeRecompensas();
            assertEquals(0.0, carteira.getAcumulado(), 0.001);
            assertEquals(0,   carteira.getMissoes());
        }
    }

    @Nested
    @DisplayName("ProcessadorDeMissao — CC baixa, NOP pequeno")
    class ProcessadorTest {

        private final ProcessadorDeMissao processor = new ProcessadorDeMissao();

        @Test
        @DisplayName("deve calcular líquida descontando a taxa")
        void deveCalcularLiquida() {
            assertEquals(850.0, processor.calcularLiquida(1000.0, 0.15), 0.001);
        }

        @Test
        @DisplayName("deve classificar missão de alto valor corretamente")
        void deveClassificarAltoValor() {
            String resultado = processor.classificar(6000.0, false);
            assertTrue(resultado.contains("ALTO VALOR"));
        }

        @Test
        @DisplayName("deve classificar missão padrão corretamente")
        void deveClassificarPadrao() {
            String resultado = processor.classificar(1500.0, false);
            assertTrue(resultado.contains("PADRÃO"));
        }

        @Test
        @DisplayName("deve classificar missão simples corretamente")
        void deveClassificarSimples() {
            String resultado = processor.classificar(500.0, false);
            assertTrue(resultado.contains("SIMPLES"));
        }

        @Test
        @DisplayName("urgente deve adicionar prefixo [URGENTE]")
        void urgenteDeveAdicionarPrefixo() {
            String resultado = processor.classificar(3000.0, true);
            assertTrue(resultado.startsWith("[URGENTE]"));
        }
    }

    @Nested
    @DisplayName("MetricasDeTamanho — avaliação por limites")
    class MetricasTest {

        @Test
        @DisplayName("NOA > 7 deve gerar alerta de God Class")
        void noaAltoDeveGerarAlerta() {
            var m = new MetricasDeTamanho("ClasseGrande", 8, 10, 4);
            assertTrue(m.avaliar().contains("NOA"));
        }

        @Test
        @DisplayName("MaxNOP > 5 deve sugerir Tiny Types")
        void nopAltoDeveSugerirTinyTypes() {
            var m = new MetricasDeTamanho("ClasseComNopAlto", 3, 5, 6);
            assertTrue(m.avaliar().contains("NOP"));
        }

        @Test
        @DisplayName("métricas saudáveis não devem gerar alertas")
        void metricasSaudaveisNaoDevemGerarAlertas() {
            var m = new MetricasDeTamanho("ClasseCoesa", 2, 3, 2);
            assertEquals("✅ Saudável", m.avaliar());
        }

        @Test
        @DisplayName("NOM > 20 deve gerar alerta de responsabilidades demais")
        void nomAltoDeveGerarAlerta() {
            var m = new MetricasDeTamanho("ClasseGrande", 3, 21, 3);
            assertTrue(m.avaliar().contains("NOM"));
        }
    }
}