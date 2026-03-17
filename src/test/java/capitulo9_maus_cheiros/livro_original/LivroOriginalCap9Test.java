package capitulo9_maus_cheiros.livro_original;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap9 — Testes das Classes Originais do Livro")
class LivroOriginalCap9Test {

    // ─── 9.1 Refused Bequest ──────────────────────────────────────────────────

    @Nested
    @DisplayName("9.1 — Refused Bequest: NotaFiscal extends Matematica")
    class RefusedBequestOriginalTest {

        @Test
        @DisplayName("Matematica.quadrado deve calcular corretamente")
        void matematicaQuadradoDeveCalcular() {
            assertEquals(50, new Matematica().quadrado(10, 5));
        }

        @Test
        @DisplayName("NotaFiscalComSmell herda Matematica — pode ser passada onde Matematica é esperada (smell)")
        void notaFiscalComSmellPodeSubstituirMatematica() {
            var nf      = new NotaFiscalComSmell(100.0, 3);
            var sistema = new SistemaComSmell();
            // ❌ semanticamente errado: sistema espera Matematica, recebe NotaFiscal
            assertDoesNotThrow(() -> sistema.algumComportamento(nf));
        }

        @Test
        @DisplayName("NotaFiscalCorreta NÃO pode ser passada onde Matematica é esperada (solução)")
        void notaFiscalCorretaNaoSubstituiMatematica() {
            NotaFiscalCorreta nf = new NotaFiscalCorreta(100.0, 3);
            // ✅ NotaFiscalCorreta não estende Matematica — type-safe
            // Este teste documenta que a relação de herança foi eliminada
            assertFalse(Matematica.class.isAssignableFrom(nf.getClass()));
        }

        @Test
        @DisplayName("NotaFiscalCorreta calcula imposto via composição")
        void notaFiscalCorretaCalculaImpostoViaComposicao() {
            var nf = new NotaFiscalCorreta(1000.0, 5);
            // 1000 * 5 = 5000, * 0.1 = 500
            assertEquals(500.0, nf.calculaImposto(), 0.001);
        }
    }

    // ─── 9.2 Feature Envy ────────────────────────────────────────────────────

    @Nested
    @DisplayName("9.2 — Feature Envy: Gerenciador vs NotaFiscal")
    class FeatureEnvyOriginalTest {

        @Test
        @DisplayName("Gerenciador.processa deve finalizar a nota (mesmo que com smell)")
        void gerenciadorProcessaDeveFinalizarNota() {
            var nf         = new NotaFiscal(1000.0, "Mauricio", 3);
            var gerenciador = new Gerenciador(new Usuario("admin"));
            gerenciador.processa(nf);
            assertTrue(nf.isFinalizada());
        }

        @Test
        @DisplayName("Gerenciador deve aplicar multiplicador para qtdDeItens > 2")
        void gerenciadorDeveAplicarMultiplicador() {
            var nf = new NotaFiscal(1000.0, "Mauricio", 3); // 3 > 2
            new Gerenciador(new Usuario("admin")).processa(nf);
            // 1000 * 0.15 * 1.1 = 165
            assertEquals(165.0, nf.getValorImposto(), 0.001);
        }

        @Test
        @DisplayName("NotaFiscalComportamental.processa deve funcionar sem Gerenciador")
        void notaFiscalComportamentalProcessaSozinha() {
            var nf = new NotaFiscalComportamental(1000.0, "Mauricio", 3);
            nf.processar(); // ✅ o comportamento está onde os dados estão
            assertTrue(nf.isFinalizada());
            assertEquals(165.0, nf.getValorImposto(), 0.001);
        }

        @Test
        @DisplayName("NotaFiscalComportamental sem itens extras não aplica multiplicador")
        void notaFiscalComportamentalSemMultiplicador() {
            var nf = new NotaFiscalComportamental(1000.0, "Mauricio", 1); // 1 <= 2
            nf.processar();
            assertEquals(150.0, nf.getValorImposto(), 0.001); // 15% puro
        }
    }

    // ─── 9.3 Intimidade Inapropriada ─────────────────────────────────────────

    @Nested
    @DisplayName("9.3 — Intimidade Inapropriada: NotaFiscal e processadores")
    class IntimidadeInapropiadaOriginalTest {

        @Test
        @DisplayName("ProcessadorComIntimidade marca nota como importante quando valor > 5000")
        void processadorComIntimidadeMarcaImportante() {
            var nf = new NotaFiscalIntimidade(8000.0);
            nf.encerrar();
            new ProcessadorComIntimidade().processa(nf);
            assertTrue(nf.isImportante());
        }

        @Test
        @DisplayName("ProcessadorComIntimidade NÃO marca se valor <= 5000")
        void processadorComIntimidadeNaoMarcaValorBaixo() {
            var nf = new NotaFiscalIntimidade(3000.0);
            nf.encerrar();
            new ProcessadorComIntimidade().processa(nf);
            assertFalse(nf.isImportante());
        }

        @Test
        @DisplayName("NotaFiscalEncapsulada marca importante automaticamente ao encerrar com valor alto")
        void notaFiscalEncapsuladaMarcaAoEncerrar() {
            var nf = new NotaFiscalEncapsulada(8000.0);
            nf.encerrar(); // ✅ regra encapsulada — ninguém precisa saber do if
            assertTrue(nf.isImportante());
        }

        @Test
        @DisplayName("NotaFiscalEncapsulada não marca importante para valor baixo")
        void notaFiscalEncapsuladaNaoMarcaValorBaixo() {
            var nf = new NotaFiscalEncapsulada(3000.0);
            nf.encerrar();
            assertFalse(nf.isImportante());
        }

        @Test
        @DisplayName("ProcessadorLimpo delega para o objeto sem conhecer internos")
        void processadorLimpoDelega() {
            var nf = new NotaFiscalEncapsulada(6000.0);
            new ProcessadorLimpo().processa(nf); // ✅ Tell, don't ask
            assertTrue(nf.isEncerrada());
            assertTrue(nf.isImportante());
        }
    }

    // ─── 9.4-9.6 Conceituais ─────────────────────────────────────────────────

    @Nested
    @DisplayName("9.4/9.5/9.6 — God Class, Divergent Changes e Shotgun Surgery")
    class ConceituaisTest {

        @Test
        @DisplayName("AliquotaDeImposto centralizada calcula 15% corretamente")
        void aliquotaCentralizadaCalculaCorretamente() {
            var aliquota = new AliquotaDeImposto();
            assertEquals(150.0, aliquota.calcular(1000.0), 0.001);
            assertEquals(850.0, aliquota.calcularLiquido(1000.0), 0.001);
        }

        @Test
        @DisplayName("EmissorDeNotaShotgun calcula o mesmo valor que AliquotaDeImposto")
        void emissoreDevemCalcularMesmoValor() {
            double valor     = 2000.0;
            var shotgun      = new EmissorDeNotaShotgun();
            var centralizado = new AliquotaDeImposto();
            // Ambos chegam ao mesmo resultado, mas só AliquotaDeImposto é fácil de mudar
            assertEquals(centralizado.calcular(valor), shotgun.calcularImposto(valor), 0.001);
        }

        @Test
        @DisplayName("ValidadorDeNotaShotgun e AliquotaDeImposto são consistentes")
        void validadorECentralizadoSaoConsistentes() {
            double bruta = 3000.0;
            var aliquota = new AliquotaDeImposto();
            var validador = new ValidadorDeNotaShotgun();

            double imposto = aliquota.calcular(bruta);
            // ✅ ambos concordam com o mesmo cálculo
            assertTrue(validador.impostoCorreto(bruta, imposto));
        }
    }
}
