package capitulo9_maus_cheiros.v3_intimidade_inapropriada;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap9 v3 — Intimidade Inapropriada / Tell Don't Ask")
class IntimidadeInapropiadaTest {

    @Nested
    @DisplayName("Missao — encapsulamento do estado")
    class MissaoTest {

        @Test
        @DisplayName("encerrar deve marcar como urgente quando valor > 5000")
        void encerrarDeveMarcarUrgenteParaValorAlto() {
            var missao = new Missao(8000.0, "EXTREMO");
            missao.aceitar();
            missao.encerrar();
            assertTrue(missao.isUrgente());
            assertTrue(missao.isEncerrada());
        }

        @Test
        @DisplayName("encerrar não deve marcar urgente quando valor <= 5000")
        void encerrarNaoDeveMarcarUrgenteParaValorBaixo() {
            var missao = new Missao(3000.0, "ALTO");
            missao.aceitar();
            missao.encerrar();
            assertFalse(missao.isUrgente());
            assertTrue(missao.isEncerrada());
        }

        @Test
        @DisplayName("calcularPrioridade deve retornar valor * 2 para EXTREMO")
        void calcularPrioridadeParaExtremo() {
            var missao = new Missao(1000.0, "EXTREMO");
            assertEquals(2000.0, missao.calcularPrioridade(), 0.001);
        }

        @Test
        @DisplayName("calcularPrioridade deve retornar valor * 1.5 para ALTO")
        void calcularPrioridadeParaAlto() {
            var missao = new Missao(1000.0, "ALTO");
            assertEquals(1500.0, missao.calcularPrioridade(), 0.001);
        }

        @Test
        @DisplayName("calcularPrioridade deve retornar valor base para nível desconhecido")
        void calcularPrioridadeParaNivelDesconhecido() {
            var missao = new Missao(1000.0, "BAIXO");
            assertEquals(1000.0, missao.calcularPrioridade(), 0.001);
        }

        @Test
        @DisplayName("aceitar missão antes de encerrar deve funcionar")
        void aceitarAntesDeCerrarDeveFuncionar() {
            var missao = new Missao(5000.0, "MEDIO");
            assertDoesNotThrow(() -> {
                missao.aceitar();
                missao.encerrar();
            });
        }
    }

    @Nested
    @DisplayName("GerenciadorDeMissoesLimpo")
    class GerenciadorTest {

        @Test
        @DisplayName("encerrarMissao deve delegar para o objeto sem intimidade inapropriada")
        void encerrarMissaoDeveDelegarParaObjeto() {
            var missao    = new Missao(9000.0, "EXTREMO");
            var gerenciador = new GerenciadorDeMissoesLimpo();
            missao.aceitar();
            gerenciador.encerrarMissao(missao);
            assertTrue(missao.isEncerrada());
            assertTrue(missao.isUrgente());
        }
    }
}
