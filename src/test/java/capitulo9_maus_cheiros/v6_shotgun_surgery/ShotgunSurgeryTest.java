package capitulo9_maus_cheiros.v6_shotgun_surgery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap9 v6 — Shotgun Surgery: TaxaDaGuilda centralizada")
class ShotgunSurgeryTest {

    private TaxaDaGuilda taxa;

    @BeforeEach
    void setUp() { taxa = new TaxaDaGuilda(); }

    @Nested
    @DisplayName("TaxaDaGuilda — cálculo centralizado")
    class TaxaDaGuildaTest {

        @Test
        @DisplayName("deve calcular 15% de taxa sobre o valor bruto")
        void deveCalcularQuinzePorCento() {
            assertEquals(300.0, taxa.calcular(2000.0), 0.001);
        }

        @Test
        @DisplayName("deve calcular recompensa líquida = bruta - taxa")
        void deveCalcularLiquidaCorretamente() {
            assertEquals(1700.0, taxa.calcularLiquida(2000.0), 0.001);
        }

        @Test
        @DisplayName("taxaEstaCorreta deve retornar true para cálculo correto")
        void taxaEstaCorretaDeveRetornarTrue() {
            assertTrue(taxa.taxaEstaCorreta(2000.0, 1700.0));
        }

        @Test
        @DisplayName("taxaEstaCorreta deve retornar false para cálculo errado")
        void taxaEstaCorretaDeveRetornarFalse() {
            assertFalse(taxa.taxaEstaCorreta(2000.0, 1800.0)); // valor errado
        }
    }

    @Nested
    @DisplayName("Serviços que dependem de TaxaDaGuilda")
    class ServicosComTaxaCentralizada {

        @Test
        @DisplayName("ProcessadorDeContratoLimpo deve calcular líquido via TaxaDaGuilda")
        void processadorDeveUsarTaxa() {
            var proc = new ProcessadorDeContratoLimpo(taxa);
            assertEquals(850.0, proc.calcularRecompensaLiquida(1000.0), 0.001);
        }

        @Test
        @DisplayName("RelatorioDeContratoLimpo deve incluir valores corretos")
        void relatorioDeveIncluirValoresCorretos() {
            var rel  = new RelatorioDeContratoLimpo(taxa);
            var linha = rel.gerarLinha("Harpia", 2000.0);
            assertTrue(linha.contains("Harpia"));
            assertTrue(linha.contains("300,00") || linha.contains("300.00")); // taxa
            assertTrue(linha.contains("1700,00") || linha.contains("1700.00")); // líquida
        }

        @Test
        @DisplayName("AuditoriaDeContratoLimpo deve validar auditoria corretamente")
        void auditoriaDeveValidarCorretamente() {
            var auditoria = new AuditoriaDeContratoLimpo(taxa);
            assertTrue(auditoria.taxaEstaCorreta(2000.0, 1700.0));
            assertFalse(auditoria.taxaEstaCorreta(2000.0, 1800.0));
        }

        @Test
        @DisplayName("todos os serviços devem ser consistentes entre si para o mesmo valor")
        void todosOsServicosDevemSerConsistentes() {
            double bruta   = 5000.0;
            var proc       = new ProcessadorDeContratoLimpo(taxa);
            var auditoria  = new AuditoriaDeContratoLimpo(taxa);

            double liquida = proc.calcularRecompensaLiquida(bruta);
            // ✅ o processador e a auditoria chegam ao mesmo resultado
            assertTrue(auditoria.taxaEstaCorreta(bruta, liquida));
        }
    }
}
