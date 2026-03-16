package capitulo8_consistencia.v1_construtor_rico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap8 v1 — Construtor Rico: Missao")
class MissaoTest {

    // ─── Construção válida ────────────────────────────────────────────────────

    @Nested
    @DisplayName("Dado uma Missao criada com atributos válidos")
    class MissaoValida {

        @Test
        @DisplayName("deve nascer com status DISPONIVEL")
        void deveNascerDisponivel() {
            var missao = new Missao("Grifo Real", "Aldeão", 1500.0, NivelDePerigo.ALTO);
            assertEquals(StatusDaMissao.DISPONIVEL, missao.getStatus());
        }

        @Test
        @DisplayName("deve armazenar os atributos corretamente")
        void deveArmazenarAtributos() {
            var missao = new Missao("Wyvern", "Mercador", 800.0, NivelDePerigo.MEDIO);
            assertEquals("Wyvern", missao.getMonstroAlvo());
            assertEquals("Mercador", missao.getClientePagador());
            assertEquals(800.0, missao.getRecompensa());
            assertEquals(NivelDePerigo.MEDIO, missao.getNivelDePerigo());
        }

        @Test
        @DisplayName("construtor de 3 parâmetros usa NivelDePerigo.MEDIO como padrão")
        void construtorTresParametrosUsaNivelMedio() {
            var missao = new Missao("Strige", "Fazendeiro", 300.0);
            assertEquals(NivelDePerigo.MEDIO, missao.getNivelDePerigo());
        }

        @Test
        @DisplayName("deve calcular taxa da guilda corretamente")
        void deveCalcularTaxaDaGuilda() {
            // ALTO tem multiplicador 0.20
            var missao = new Missao("Manticora", "Rei Foltest", 2000.0, NivelDePerigo.ALTO);
            assertEquals(400.0, missao.calcularTaxaDaGuilda(), 0.001);
        }

        @Test
        @DisplayName("deve calcular recompensa líquida corretamente")
        void deveCalcularRecompensaLiquida() {
            var missao = new Missao("Basilisco", "Nobre", 1000.0, NivelDePerigo.BAIXO);
            // BAIXO = 10% de taxa → líquida = 900
            assertEquals(900.0, missao.calcularRecompensaLiquida(), 0.001);
        }
    }

    // ─── Construção inválida ──────────────────────────────────────────────────

    @Nested
    @DisplayName("Dado atributos inválidos no construtor")
    class MissaoInvalida {

        @Test
        @DisplayName("não deve criar Missao com monstro nulo")
        void naoDeveCriarComMonstroNulo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Missao(null, "Cliente", 500.0, NivelDePerigo.BAIXO));
        }

        @Test
        @DisplayName("não deve criar Missao com monstro em branco")
        void naoDeveCriarComMonstroEmBranco() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Missao("   ", "Cliente", 500.0, NivelDePerigo.BAIXO));
        }

        @Test
        @DisplayName("não deve criar Missao com cliente nulo")
        void naoDeveCriarComClienteNulo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Missao("Grifo", null, 500.0, NivelDePerigo.BAIXO));
        }

        @Test
        @DisplayName("não deve criar Missao com recompensa zero")
        void naoDeveCriarComRecompensaZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Missao("Grifo", "Cliente", 0.0, NivelDePerigo.BAIXO));
        }

        @Test
        @DisplayName("não deve criar Missao com recompensa negativa")
        void naoDeveCriarComRecompensaNegativa() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Missao("Grifo", "Cliente", -100.0, NivelDePerigo.BAIXO));
        }
    }

    // ─── Ciclo de vida ────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Ciclo de vida da Missao")
    class CicloDeVida {

        @Test
        @DisplayName("deve aceitar missão disponível e mudar status para EM_ANDAMENTO")
        void deveAceitarMissaoDisponivel() {
            var missao = new Missao("Grifo", "Cliente", 500.0);
            missao.aceitar();
            assertEquals(StatusDaMissao.EM_ANDAMENTO, missao.getStatus());
        }

        @Test
        @DisplayName("deve concluir missão em andamento e mudar status para CONCLUIDA")
        void deveConcluirMissaoEmAndamento() {
            var missao = new Missao("Grifo", "Cliente", 500.0);
            missao.aceitar();
            missao.concluir();
            assertEquals(StatusDaMissao.CONCLUIDA, missao.getStatus());
        }

        @Test
        @DisplayName("não deve aceitar missão já em andamento")
        void naoDeveAceitarMissaoJaEmAndamento() {
            var missao = new Missao("Grifo", "Cliente", 500.0);
            missao.aceitar();
            assertThrows(IllegalStateException.class, missao::aceitar);
        }

        @Test
        @DisplayName("não deve concluir missão disponível sem aceitar primeiro")
        void naoDeveConcluirSemAceitar() {
            var missao = new Missao("Grifo", "Cliente", 500.0);
            assertThrows(IllegalStateException.class, missao::concluir);
        }

        @Test
        @DisplayName("lista de requisitos deve ser imutável externamente")
        void listaDeRequisitosDeveSerImutavel() {
            var missao = new Missao("Grifo", "Cliente", 500.0);
            missao.adicionarRequisito("Poção de Swallow");
            assertThrows(UnsupportedOperationException.class,
                    () -> missao.getRequisitos().add("Hacking externo"));
        }
    }
}
