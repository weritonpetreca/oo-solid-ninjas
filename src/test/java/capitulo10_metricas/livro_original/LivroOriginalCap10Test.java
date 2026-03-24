package capitulo10_metricas.livro_original;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap10 — Testes das Classes Originais do Livro")
class LivroOriginalCap10Test {

    // ─── 10.1 Complexidade Ciclomática ────────────────────────────────────────

    @Nested
    @DisplayName("10.1 — Complexidade Ciclomática (McCabe)")
    class ComplexidadeCiclomaticaTest {

        private final CalculadorDeCC calc = new CalculadorDeCC();

        @Test
        @DisplayName("método com dois ifs deve ter CC = 3")
        void doisIfsDevemRetornarCC3() {
            String codigo = "if(a>10) total+=a+b; if(b>20) total+=a*2+b;";
            assertEquals(3, calc.calcular(codigo));
        }

        @Test
        @DisplayName("método sem desvios deve ter CC = 1")
        void semDesviosDeveTerCC1() {
            String codigo = "return valorBase * aliquota;";
            assertEquals(1, calc.calcular(codigo));
        }

        @Test
        @DisplayName("método com for e while deve acumular desvios")
        void forEWhileDevemAcumularDesvios() {
            String codigo = "for(int i=0;i<n;i++) { while(x>0) { x--; } }";
            // for + while = 2 desvios + 1 = 3
            assertEquals(3, calc.calcular(codigo));
        }

        @Test
        @DisplayName("calcularParaClasse deve somar CC de todos os métodos")
        void calcularParaClasseDeveSomarCCDeMetodos() {
            // NotaFiscal: calculaImposto() CC=3 + encerra() CC=5 = 8
            assertEquals(8, calc.calcularParaClasse(3, 5));
        }

        @Test
        @DisplayName("calcularParaClasse com múltiplos métodos")
        void calcularParaClasseComMultiplosMetodos() {
            assertEquals(10, calc.calcularParaClasse(2, 3, 5));
        }

        @Test
        @DisplayName("método conta() do livro deve ter CC = 3")
        void metodoConta() {
            ExemploComplexidadeCiclomatica ex = new ExemploComplexidadeCiclomatica();
            // Verificando comportamento: 4 caminhos possíveis
            assertEquals(0, ex.conta(5,  5));   // ambas falsas
            assertEquals(20, ex.conta(15, 5));   // só primeira verdadeira
            assertEquals(35, ex.conta(5, 25));   // só segunda verdadeira
            assertEquals(95, ex.conta(15, 25));  // ambas verdadeiras
        }
    }

    // ─── 10.2 Tamanho ─────────────────────────────────────────────────────────

    @Nested
    @DisplayName("10.2 — Tamanho de Métodos e Classes")
    class TamanhoTest {

        @Test
        @DisplayName("ClasseCoesa deve ser instanciável com atributos válidos")
        void classeCoesaDeveInstanciar() {
            var c = new ClasseCoesa("Teste", 1000.0);
            assertEquals("Teste", c.getNome());
            assertEquals(1000.0, c.getValor(), 0.001);
        }

        @Test
        @DisplayName("ClasseCoesa.calcular deve aplicar 15% corretamente")
        void classeCoesaDeveCalcular() {
            var c = new ClasseCoesa("Teste", 1000.0);
            assertEquals(150.0, c.calcular(), 0.001);
        }
    }

    // ─── 10.3 LCOM ────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("10.3 — Coesão: NotaFiscalCoesa vs ClasseComDuasResponsabilidades")
    class CoesaoTest {

        @Test
        @DisplayName("NotaFiscalCoesa deve encerrar e calcular imposto corretamente")
        void notaFiscalCoesaDeveEncerrarECalcular() {
            var nf = new NotaFiscalCoesa(100.0);
            
            // Antes de encerrar, o imposto deve ser 0
            assertEquals(0.0, nf.calcularImposto(), 0.001);
            
            nf.encerrar();
            
            // Após encerrar, deve calcular 15% de 100 = 15
            assertEquals(15.0, nf.calcularImposto(), 0.001);
        }

        @Test
        @DisplayName("NotaFiscalCoesa não deve encerrar se não possuir valor")
        void notaFiscalNaoDeveEncerrarSemValor() {
            var nf = new NotaFiscalCoesa(0.0);
            
            // A regra de negócio proíbe encerrar nota zerada
            assertThrows(IllegalStateException.class, nf::encerrar);
        }

        @Test
        @DisplayName("NotaFiscalCoesa ao ser cancelada deve zerar valor e status")
        void notaFiscalDeveCancelarCorretamente() {
            var nf = new NotaFiscalCoesa(200.0);
            nf.encerrar();
            assertEquals(30.0, nf.calcularImposto(), 0.001);
            
            nf.cancelar();
            
            // Voltou ao estado inicial
            assertEquals(0.0, nf.calcularImposto(), 0.001);
        }

        @Test
        @DisplayName("ClasseComDuasResponsabilidades tem grupos de métodos independentes")
        void classeComDuasResponsabilidadesTemGruposIndependentes() {
            // O fato de compilar com dois grupos independentes já evidencia LCOM alto
            // Aqui verificamos que a divisão dos grupos é possível sem quebrar nada
            var cls = new ClasseComDuasResponsabilidades();
            // Grupo A — métodos de imposto (não usam email nem template)
            assertDoesNotThrow(cls::calcularImposto);
            assertDoesNotThrow(cls::calcularLiquido);
            // Grupo B — métodos de notificação (não usam valorBase nem aliquota)
            assertDoesNotThrow(cls::gerarMensagem);
        }
    }

    // ─── 10.4 Acoplamento ─────────────────────────────────────────────────────

    @Nested
    @DisplayName("10.4 — Acoplamento Aferente e Eferente")
    class AcoplamentoTest {

        @Test
        @DisplayName("GerenciadorDeNotaFiscal deve processar sem lançar exceção")
        void gerenciadorDeveProcessar() {
            var gerenciador = new GerenciadorDeNotaFiscal();
            // Precisamos passar um valor na criação da nota agora
            var nf          = new NotaFiscalCoesa(1000.0);
            assertDoesNotThrow(() -> gerenciador.processar(nf));
        }
    }

    // ─── 10.5 Nomenclatura ────────────────────────────────────────────────────

    @Nested
    @DisplayName("10.5 — Boas vs Más Práticas de Nomenclatura")
    class NomenclaturaTest {

        @Test
        @DisplayName("calcularTaxaDaGuilda com bom nome deve retornar 15%")
        void calcularTaxaDaGuildaDeveRetornarQuinzePorcento() {
            var ex = new ExemplosDeMaNomenclatura();
            assertEquals(150.0, ex.calcularTaxaDaGuilda(1000.0), 0.001);
        }

        @Test
        @DisplayName("método 'c' com nome ruim deve retornar o mesmo resultado")
        void metodoCComNomeRuimDeveRetornarMesmoResultado() {
            var ex = new ExemplosDeMaNomenclatura();
            // Mesmo resultado, mas c() não comunica intenção
            assertEquals(ex.calcularTaxaDaGuilda(1000.0), ex.c(1000.0, 1.0), 0.001);
        }
    }
}
