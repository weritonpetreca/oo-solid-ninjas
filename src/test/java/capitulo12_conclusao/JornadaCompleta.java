package capitulo12_conclusao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap12 — Conclusão: Jornada Completa do Bruxo Programador")
class JornadaCompletaTest {

    // ─── Tiny Types ──────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Tiny Types (Cap. 8 aplicado)")
    class TinyTypesTest {

        @Test
        @DisplayName("NomeDeBruxo deve rejeitar nome vazio")
        void nomeDeveRejeitarVazio() {
            assertThrows(IllegalArgumentException.class, () -> new NomeDeBruxo("  "));
        }

        @Test
        @DisplayName("NomeDeBruxo deve aceitar nome válido")
        void nomeDeveAceitarValido() {
            assertDoesNotThrow(() -> new NomeDeBruxo("Geralt de Rívia"));
        }

        @Test
        @DisplayName("RecompensaBruta deve rejeitar valor negativo")
        void recompensaDeveRejeitarNegativa() {
            assertThrows(IllegalArgumentException.class, () -> new RecompensaBruta(-1.0));
        }

        @Test
        @DisplayName("RecompensaBruta deve aceitar zero")
        void recompensaDeveAceitarZero() {
            assertDoesNotThrow(() -> new RecompensaBruta(0.0));
        }
    }

    // ─── Imutabilidade ────────────────────────────────────────────────────────

    @Nested
    @DisplayName("LocalizacaoAtual — Imutabilidade (Cap. 8 aplicado)")
    class ImutabilidadeTest {

        @Test
        @DisplayName("moverPara deve retornar nova instância")
        void moverParaDeveRetornarNovaInstancia() {
            var original = new LocalizacaoAtual("Kaer Morhen", "Fortaleza");
            var nova     = original.moverPara("Novigrad", "Porto");

            assertEquals("Kaer Morhen", original.regiao()); // original preservada
            assertEquals("Novigrad",    nova.regiao());
            assertNotSame(original, nova);
        }
    }

    // ─── Null Object ──────────────────────────────────────────────────────────

    @Nested
    @DisplayName("MensageiroDesconhecido — Null Object (Cap. 8 aplicado)")
    class NullObjectTest {

        @Test
        @DisplayName("MensageiroDesconhecido não deve lançar NPE")
        void naoDeveLancarNPE() {
            InformacaoDeContato contato = new MensageiroDesconhecido();
            assertDoesNotThrow(() -> contato.enviarMensagem("Olá"));
            assertFalse(contato.isDisponivel());
        }

        @Test
        @DisplayName("MensageiroReal deve indicar disponibilidade")
        void mensageiroRealDeveIndicarDisponibilidade() {
            InformacaoDeContato contato = new MensageiroReal("geralt@lobo.com");
            assertTrue(contato.isDisponivel());
            assertTrue(contato.enviarMensagem("Teste").contains("geralt@lobo.com"));
        }
    }

    // ─── Strategy / OCP ──────────────────────────────────────────────────────

    @Nested
    @DisplayName("Calculadores de Taxa — Strategy Pattern (Cap. 4 e 6 aplicados)")
    class StrategyTest {

        @Test
        @DisplayName("TaxaPadrao deve calcular 15%")
        void taxaPadraoDeveCalcular15Porcento() {
            assertEquals(150.0, new TaxaPadrao().calcular(1000.0), 0.001);
        }

        @Test
        @DisplayName("TaxaVeterano deve calcular 10%")
        void taxaVeteranoDeveCalcular10Porcento() {
            assertEquals(100.0, new TaxaVeterano().calcular(1000.0), 0.001);
        }

        @Test
        @DisplayName("TaxaMestre deve calcular 5%")
        void taxaMestreDeveCalcular5Porcento() {
            assertEquals(50.0, new TaxaMestre().calcular(1000.0), 0.001);
        }
    }

    // ─── BruxoDoContinente ────────────────────────────────────────────────────

    @Nested
    @DisplayName("BruxoDoContinente — síntese de todos os capítulos")
    class BruxoDoContinenteTest {

        private BruxoDoContinente criarGeralt() {
            return new BruxoDoContinente(
                    new NomeDeBruxo("Geralt"),
                    new EscolaDeBruxo("Escola do Lobo"),
                    new TaxaVeterano(),
                    new MensageiroReal("geralt@kaermorhen.com"),
                    new LocalizacaoAtual("Kaer Morhen", "Fortaleza")
            );
        }

        @Test
        @DisplayName("deve ser criado com construtor rico")
        void deveSerCriadoComConstrutorRico() {
            var bruxo = criarGeralt();
            assertEquals("Geralt", bruxo.getNome().valor());
            assertEquals("Escola do Lobo", bruxo.getEscola().nome());
        }

        @Test
        @DisplayName("deve registrar missão e acumular recompensa líquida")
        void deveRegistrarMissaoEAcumularLiquida() {
            var bruxo = criarGeralt();
            bruxo.registrarMissaoConcluida("Grifo", new RecompensaBruta(1000.0));
            // Taxa veterano = 10% → líquida = 900
            assertEquals(900.0, bruxo.getRecompensaAcumulada(), 0.001);
        }

        @Test
        @DisplayName("deve calcular imposto como Tributavel (ISP — Cap. 7)")
        void deveCalcularImpostoComoTributavel() {
            var bruxo = criarGeralt();
            bruxo.registrarMissaoConcluida("Grifo",    new RecompensaBruta(1000.0));
            bruxo.registrarMissaoConcluida("Basilisco",new RecompensaBruta(2000.0));
            // Acumulado líquido = 900 + 1800 = 2700
            // Imposto (veterano 10%) sobre 2700 = 270
            double imposto = bruxo.calcularImposto();
            assertEquals(270.0, imposto, 0.001);
        }

        @Test
        @DisplayName("moverPara deve atualizar localização sem criar nova instância da classe")
        void moverParaDeveAtualizarLocalizacao() {
            var bruxo = criarGeralt();
            bruxo.moverPara("Novigrad", "Porto");
            assertEquals("Novigrad", bruxo.getLocalizacao().regiao());
        }

        @Test
        @DisplayName("getMissoesConcluidass deve retornar lista imutável (Cap. 5)")
        void getMissoesConcluidasDeveSerImutavel() {
            var bruxo = criarGeralt();
            bruxo.registrarMissaoConcluida("Grifo", new RecompensaBruta(1000.0));
            assertThrows(UnsupportedOperationException.class,
                    () -> bruxo.getMissoesConcluidass().add("Hack externo"));
        }

        @Test
        @DisplayName("notificar com MensageiroDesconhecido não deve lançar NPE (Null Object)")
        void notificarComDesconhecidoNaoDeveLancarNPE() {
            var bruxo = new BruxoDoContinente(
                    new NomeDeBruxo("Lambert"),
                    new EscolaDeBruxo("Escola do Lobo"),
                    new TaxaPadrao(),
                    new MensageiroDesconhecido(),
                    new LocalizacaoAtual("Kaer Morhen", "Fortaleza")
            );
            assertDoesNotThrow(() -> bruxo.notificar("Mensagem de teste"));
            assertFalse(bruxo.temContato());
        }
    }

    // ─── RegistroDeBruxos ─────────────────────────────────────────────────────

    @Nested
    @DisplayName("RegistroDeBruxos — Optional sem null (Cap. 8)")
    class RegistroDeBruxosTest {

        @Test
        @DisplayName("buscarPorNome deve retornar Optional com bruxo existente")
        void deveBuscarBruxoExistente() {
            var registro = new RegistroDeBruxos();
            registro.registrar(new BruxoDoContinente(
                    new NomeDeBruxo("Ciri"), new EscolaDeBruxo("Lobo"),
                    new TaxaMestre(), new MensageiroDesconhecido(),
                    new LocalizacaoAtual("Toussaint", "Beauclair")));
            assertTrue(registro.buscarPorNome("Ciri").isPresent());
        }

        @Test
        @DisplayName("buscarPorNome deve ser case-insensitive")
        void deveBuscarCaseInsensitive() {
            var registro = new RegistroDeBruxos();
            registro.registrar(new BruxoDoContinente(
                    new NomeDeBruxo("Geralt"), new EscolaDeBruxo("Lobo"),
                    new TaxaVeterano(), new MensageiroDesconhecido(),
                    new LocalizacaoAtual("Velen", "Vizima")));
            assertTrue(registro.buscarPorNome("geralt").isPresent());
            assertTrue(registro.buscarPorNome("GERALT").isPresent());
        }

        @Test
        @DisplayName("buscarPorNome deve retornar Optional vazio para bruxo inexistente")
        void deveRetornarOptionalVazioParaInexistente() {
            var registro = new RegistroDeBruxos();
            assertTrue(registro.buscarPorNome("Vesemir").isEmpty());
        }

        @Test
        @DisplayName("listarDisponiveis deve retornar apenas bruxos disponíveis")
        void deveListarApenasBruxosDisponiveis() {
            var registro = new RegistroDeBruxos();
            registro.registrar(new BruxoDoContinente(
                    new NomeDeBruxo("Geralt"), new EscolaDeBruxo("Lobo"),
                    new TaxaVeterano(), new MensageiroDesconhecido(),
                    new LocalizacaoAtual("Velen", "Vizima")));
            assertEquals(1, registro.listarDisponiveis().size());
        }
    }
}