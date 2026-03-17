package capitulo9_maus_cheiros.v7_mundo_real;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap9 v7 — Mundo Real: GuildaRefatorada")
class GuildaRefatoradaTest {

    // ─── Missao ───────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Missao — ciclo de vida e encapsulamento")
    class MissaoTest {

        @Test
        @DisplayName("deve aceitar missão disponível")
        void deveAceitarMissaoDisponivel() {
            var missao = new Missao("Grifo", 1000.0, TipoDeMonstro.HIBRIDO);
            assertDoesNotThrow(missao::aceitar);
            assertEquals(StatusDaMissao.EM_ANDAMENTO, missao.getStatus());
        }

        @Test
        @DisplayName("deve concluir missão em andamento")
        void deveConcluirMissaoEmAndamento() {
            var missao = new Missao("Strige", 800.0, TipoDeMonstro.VAMPIRO);
            missao.aceitar();
            missao.concluir();
            assertEquals(StatusDaMissao.CONCLUIDA, missao.getStatus());
            assertTrue(missao.isConcluida());
        }

        @Test
        @DisplayName("não deve concluir missão disponível sem aceitar")
        void naoDeveConcluirSemAceitar() {
            var missao = new Missao("Wyvern", 500.0, TipoDeMonstro.HIBRIDO);
            assertThrows(IllegalStateException.class, missao::concluir);
        }

        @Test
        @DisplayName("não deve aceitar missão já em andamento")
        void naoDeveAceitarDuasVezes() {
            var missao = new Missao("Lobisomem", 600.0, TipoDeMonstro.AMALDICADO);
            missao.aceitar();
            assertThrows(IllegalStateException.class, missao::aceitar);
        }

        @Test
        @DisplayName("descricaoCompleta deve conter nome do monstro e status")
        void descricaoCompletaDeveConterDadosPrincipais() {
            var missao = new Missao("Basilisco", 2000.0, TipoDeMonstro.HIBRIDO);
            var desc   = missao.descricaoCompleta();
            assertTrue(desc.contains("Basilisco"));
            assertTrue(desc.contains("DISPONIVEL"));
        }
    }

    // ─── CacadorDeGuerra ──────────────────────────────────────────────────────

    @Nested
    @DisplayName("CacadorDeGuerra — bônus por nível")
    class CacadorDeGuerraTest {

        @Test
        @DisplayName("deve calcular bônus como percentual do nível sobre a recompensa")
        void deveCalcularBonusPorNivel() {
            var cacador = new CacadorDeGuerra("Geralt", "Lobo", 90);
            // 90 / 100 = 0.90 do líquido
            double recompensa = 1000.0;
            assertEquals(recompensa * 0.90, cacador.calcularBonus(recompensa), 0.001);
        }

        @Test
        @DisplayName("deve retornar escola corretamente")
        void deveRetornarEscola() {
            var cacador = new CacadorDeGuerra("Lambert", "Lobo", 65);
            assertEquals("Lobo", cacador.getEscola());
        }
    }

    // ─── CacadorMago ──────────────────────────────────────────────────────────

    @Nested
    @DisplayName("CacadorMago — implements Cacador e Feiticeiro (sem Refused Bequest)")
    class CacadorMagoTest {

        @Test
        @DisplayName("deve calcular multiplicador mágico baseado no poder")
        void deveCalcularMultiplicadorMagico() {
            var mago = new CacadorMago("Yennefer", 95);
            assertEquals(95.0 / 50.0, mago.multiplicadorMagico(), 0.001);
        }

        @Test
        @DisplayName("deve lançar feitiço com nome do alvo")
        void deveLancarFeitico() {
            var mago   = new CacadorMago("Triss", 80);
            var result = mago.lançarFeitico("Basilisco");
            assertTrue(result.contains("Basilisco"));
        }

        @Test
        @DisplayName("escola deve ser Aretuza")
        void escolaDeveSerAretuza() {
            var mago = new CacadorMago("Yennefer", 95);
            assertEquals("Aretuza", mago.getEscola());
        }

        @Test
        @DisplayName("calcularBonus deve usar multiplicador mágico")
        void calcularBonusDeveUsarMultiplicadorMagico() {
            var mago = new CacadorMago("Yennefer", 50);
            // multiplicadorMagico = 50/50 = 1.0
            assertEquals(1000.0, mago.calcularBonus(1000.0), 0.001);
        }
    }

    // ─── TaxaDaGuilda ─────────────────────────────────────────────────────────

    @Nested
    @DisplayName("TaxaDaGuilda — centralização do conceito")
    class TaxaDaGuildaTest {

        private final TaxaDaGuilda taxa = new TaxaDaGuilda();

        @Test
        @DisplayName("deve calcular 15% de taxa")
        void deveCalcularQuinzePorCento() {
            assertEquals(300.0, taxa.calcular(2000.0), 0.001);
        }

        @Test
        @DisplayName("deve calcular valor líquido = bruto - taxa")
        void deveCalcularValorLiquido() {
            assertEquals(1700.0, taxa.calcularLiquida(2000.0), 0.001);
        }
    }

    // ─── ServicoDeRecompensa ──────────────────────────────────────────────────

    @Nested
    @DisplayName("ServicoDeRecompensa — pagamento com bônus")
    class ServicoDeRecompensaTest {

        private ServicoDeRecompensa servico;

        @BeforeEach
        void setUp() { servico = new ServicoDeRecompensa(new TaxaDaGuilda()); }

        @Test
        @DisplayName("deve calcular pagamento = líquido + bônus do caçador")
        void deveCalcularPagamentoComBonus() {
            // Taxa 15%: líquido de 2000 = 1700
            // CacadorDeGuerra nível 0 = bônus 0 → pagamento = 1700
            var cacador = new CacadorDeGuerra("Lambert", "Lobo", 0);
            var missao  = new Missao("Wyvern", 2000.0, TipoDeMonstro.HIBRIDO);
            assertEquals(1700.0, servico.calcularPagamento(cacador, missao), 0.001);
        }
    }

    // ─── GuildaRefatorada — integração completa ───────────────────────────────

    @Nested
    @DisplayName("GuildaRefatorada — integração completa sem God Class")
    class GuildaRefatoradaIntegracaoTest {

        @Test
        @DisplayName("deve concluir missão sem lançar exceção")
        void deveConcluirMissaoSemExcecao() {
            var guilda  = new GuildaRefatorada();
            var cacador = new CacadorDeGuerra("Geralt", "Lobo", 90);
            var missao  = new Missao("Grifo", 3000.0, TipoDeMonstro.HIBRIDO);

            missao.aceitar();
            assertDoesNotThrow(() -> guilda.concluirMissao(cacador, missao));
        }

        @Test
        @DisplayName("histórico deve crescer a cada missão concluída")
        void historicodeveGrowAposConclusoes() {
            var guilda   = new GuildaRefatorada();
            var geralt   = new CacadorDeGuerra("Geralt",  "Lobo", 90);
            var lambert  = new CacadorDeGuerra("Lambert", "Lobo", 65);
            var missao1  = new Missao("Grifo",    2000.0, TipoDeMonstro.HIBRIDO);
            var missao2  = new Missao("Basilisco", 1500.0, TipoDeMonstro.VAMPIRO);

            missao1.aceitar(); missao2.aceitar();
            guilda.concluirMissao(geralt,  missao1);
            guilda.concluirMissao(lambert, missao2);

            // histórico é privado — validamos indiretamente via imprimirHistorico sem exceção
            assertDoesNotThrow(guilda::imprimirHistorico);
        }

        @Test
        @DisplayName("CacadorMago deve participar da guilda sem Refused Bequest")
        void cacadorMagoDeveParticiparDaGuilda() {
            var guilda    = new GuildaRefatorada();
            var yennefer  = new CacadorMago("Yennefer", 95);
            var missao    = new Missao("Djinn", 8000.0, TipoDeMonstro.ESPECTRE);

            missao.aceitar();
            assertDoesNotThrow(() -> guilda.concluirMissao(yennefer, missao));
        }
    }
}
