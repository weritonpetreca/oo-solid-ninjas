package capitulo10_metricas.v1_complexidade_ciclomatica;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap10 v1 — Complexidade Ciclomática (Witcher)")
class ComplexidadeCiclomaticaTest {

    // ─── EstrategiasDeCacaComAltaCC ──────────────────────────────────────────

    @Nested
    @DisplayName("EstrategiasDeCacaComAltaCC — 12 caminhos de execução")
    class AltaCCTest {

        private final EstrategiasDeCacaComAltaCC estrategia = new EstrategiasDeCacaComAltaCC();

        @Test
        @DisplayName("Grifo com chuva deve usar armadilha")
        void grifoComChuvaDeveUsarArmadilha() {
            assertEquals("USAR_ARMADILHA",
                    estrategia.definirEstrategia("GRIFO", "CHUVA", "QUALQUER"));
        }

        @Test
        @DisplayName("Grifo sem chuva deve usar ataque direto")
        void grifoSemChuvaDeveUsarAtaqueDireto() {
            assertEquals("ATAQUE_DIRETO",
                    estrategia.definirEstrategia("GRIFO", "SOL", "ESPADA_PRATA"));
        }

        @Test
        @DisplayName("Strige de noite com espada de prata deve atacar")
        void strigeDeNoiteComEspadaDeveatacaNOTURNO() {
            assertEquals("ATAQUE_NOTURNO",
                    estrategia.definirEstrategia("STRIGE", "NOITE", "ESPADA_PRATA"));
        }

        @Test
        @DisplayName("Strige de noite sem espada deve recuar")
        void strigeDeNoiteSemEspadaDeveRecuar() {
            assertEquals("RECUAR",
                    estrategia.definirEstrategia("STRIGE", "NOITE", "ESPADA_ACO"));
        }

        @Test
        @DisplayName("Lobisomem em lua cheia sem espada deve fugir")
        void lobisomemLuaCheíaSemEspadaDeveFugir() {
            assertEquals("FUGIR",
                    estrategia.definirEstrategia("LOBISOMEM", "LUA_CHEIA", "NENHUMA"));
        }

        @Test
        @DisplayName("monstro desconhecido deve retornar ESPERAR")
        void monstroDesconhecidoDeveRetornarEsperar() {
            assertEquals("ESPERAR",
                    estrategia.definirEstrategia("DRAGAO", "SOL", "QUALQUER"));
        }
    }

    // ─── BruxoEstrategista com polimorfismo ──────────────────────────────────

    @Nested
    @DisplayName("BruxoEstrategista — CC = 1, zero IFs no método decidir()")
    class BruxoEstrategistaTest {

        private final BruxoEstrategista bruxo = new BruxoEstrategista();

        @Test
        @DisplayName("Grifo em chuva deve usar armadilha — mesmo resultado sem IF")
        void grifoEmChuvaDeveUsarArmadilha() {
            assertEquals("USAR_ARMADILHA",
                    bruxo.decidir(new Grifo(), "CHUVA", "ESPADA_PRATA"));
        }

        @Test
        @DisplayName("Strige de dia deve caçar diurno independente da arma")
        void strigeDeDiaDeveCacarDiurno() {
            assertEquals("CACAR_DIURNO",
                    bruxo.decidir(new Strige(), "DIA", "QUALQUER"));
        }

        @Test
        @DisplayName("Lobisomem em lua cheia com espada de prata deve lutar")
        void lobisomemLuaCheiaComEspadaDeveLutar() {
            assertEquals("LUTA_DIRETA",
                    bruxo.decidir(new Lobisomem(), "LUA_CHEIA", "ESPADA_PRATA"));
        }

        @Test
        @DisplayName("Resultado deve ser idêntico ao da versão com IFs")
        void resultadoDeveSerIdenticoAVersaoComIfs() {
            var comIfs    = new EstrategiasDeCacaComAltaCC();
            var semIfs    = new BruxoEstrategista();

            // Grifo + Sol: ambos devem retornar ATAQUE_DIRETO
            assertEquals(
                    comIfs.definirEstrategia("GRIFO", "SOL", "ESPADA_PRATA"),
                    semIfs.decidir(new Grifo(), "SOL", "ESPADA_PRATA")
            );
            // Strige + Noite + Prata: ambos devem retornar ATAQUE_NOTURNO
            assertEquals(
                    comIfs.definirEstrategia("STRIGE", "NOITE", "ESPADA_PRATA"),
                    semIfs.decidir(new Strige(), "NOITE", "ESPADA_PRATA")
            );
        }
    }

    // ─── RelatorioDeCC ────────────────────────────────────────────────────────

    @Nested
    @DisplayName("RelatorioDeCC — avaliação por faixas")
    class RelatorioDeCCTest {

        @Test
        @DisplayName("CC = 1 deve ser avaliado como EXCELENTE")
        void cc1DeveSerExcelente() {
            var m = RelatorioDeCC.MetodoAnalisado.de("metodo()", 0); // 0 desvios = CC 1
            assertEquals(1, m.cc());
            assertTrue(m.avaliacao().contains("EXCELENTE"));
        }

        @Test
        @DisplayName("CC = 5 deve ser avaliado como ACEITÁVEL")
        void cc5DeveSerAceitavel() {
            var m = RelatorioDeCC.MetodoAnalisado.de("metodo()", 4); // 4 desvios = CC 5
            assertEquals(5, m.cc());
            assertTrue(m.avaliacao().contains("ACEITÁVEL"));
        }

        @Test
        @DisplayName("CC = 12 deve ser avaliado como REFATORA")
        void cc12DeveSerRefatora() {
            var m = RelatorioDeCC.MetodoAnalisado.de("metodo()", 11); // 11 desvios = CC 12
            assertEquals(12, m.cc());
            assertTrue(m.avaliacao().contains("REFATORA"));
        }

        @Test
        @DisplayName("CC = 8 deve ser avaliado como ATENÇÃO")
        void cc8DeveSerAtencao() {
            var m = RelatorioDeCC.MetodoAnalisado.de("metodo()", 7);
            assertEquals(8, m.cc());
            assertTrue(m.avaliacao().contains("ATENÇÃO"));
        }
    }
}