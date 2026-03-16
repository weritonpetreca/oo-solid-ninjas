package capitulo8_consistencia.v9_mundo_real;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap8 v9 — Mundo Real: CadastroDeGuilda")
class CadastroDeGuildaTest {

    private CadastroDeGuilda guilda;

    @BeforeEach
    void setUp() {
        guilda = new CadastroDeGuilda();
    }

    // ─── Registro bem-sucedido ────────────────────────────────────────────────

    @Nested
    @DisplayName("Dado um registro com dados válidos")
    class RegistroValido {

        @Test
        @DisplayName("deve registrar caçador e retornar sucesso")
        void deveRegistrarComSucesso() {
            var resultado = guilda.registrar("Geralt", "Lobo", "G01-LOBO", "Kaer Morhen", "Fortaleza");
            assertTrue(resultado.isSucesso());
            assertNotNull(resultado.getCacador());
        }

        @Test
        @DisplayName("caçador registrado deve estar ativo")
        void cacadorRegistradoDeveEstarAtivo() {
            var resultado = guilda.registrar("Geralt", "Lobo", "G01-LOBO", "Kaer Morhen", "Fortaleza");
            assertTrue(resultado.getCacador().isAtivo());
        }

        @Test
        @DisplayName("nome do caçador deve ser acessível após registro")
        void nomeDoCacadorDeveSerAcessivel() {
            var resultado = guilda.registrar("Ciri", "Lobo", "C99-LOBO", "Novigrad", "Palácio");
            assertEquals("Ciri", resultado.getCacador().getNome().get());
        }

        @Test
        @DisplayName("total de ativos deve aumentar após registro")
        void totalAtivoDeveAumentarAposRegistro() {
            guilda.registrar("Geralt",  "Lobo", "G01-LOBO", "Kaer Morhen", "Fortaleza");
            guilda.registrar("Lambert", "Lobo", "L03-LOBO", "Oxenfurt",    "Taverna");
            assertEquals(2, guilda.listarAtivos().size());
        }
    }

    // ─── Registro com falha ───────────────────────────────────────────────────

    @Nested
    @DisplayName("Dado um registro com dados inválidos")
    class RegistroInvalido {

        @Test
        @DisplayName("deve retornar falha com nome em branco")
        void deveRetornarFalhaNomeEmBranco() {
            var resultado = guilda.registrar("", "Lobo", "G01-LOBO", "Kaer Morhen", "Fortaleza");
            assertFalse(resultado.isSucesso());
            assertFalse(resultado.getErros().isEmpty());
        }

        @Test
        @DisplayName("deve retornar falha com escola nula")
        void deveRetornarFalhaEscolaNula() {
            var resultado = guilda.registrar("Geralt", null, "G01-LOBO", "Kaer Morhen", "Fortaleza");
            assertFalse(resultado.isSucesso());
        }

        @Test
        @DisplayName("deve retornar falha com credencial inválida")
        void deveRetornarFalhaCredencialInvalida() {
            var resultado = guilda.registrar("Geralt", "Lobo", "invalida!", "Kaer Morhen", "Fortaleza");
            assertFalse(resultado.isSucesso());
        }

        @Test
        @DisplayName("falha não deve incrementar contagem de ativos")
        void falhaDeveNaoIncrementarAtivos() {
            guilda.registrar("", "Lobo", "G01-LOBO", "Kaer Morhen", "Fortaleza");
            assertEquals(0, guilda.listarAtivos().size());
        }
    }

    // ─── Busca ────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Busca de caçadores")
    class BuscaDeCacadores {

        @BeforeEach
        void registrarCacadores() {
            guilda.registrar("Geralt", "Lobo", "G01-LOBO", "Kaer Morhen", "Fortaleza");
            guilda.registrar("Ciri",   "Lobo", "C99-LOBO", "Novigrad",    "Palácio");
        }

        @Test
        @DisplayName("buscarPorNome deve encontrar caçador existente")
        void deveBuscarCacadorExistente() {
            var encontrado = guilda.buscarPorNome("Geralt");
            assertTrue(encontrado.isPresent());
            assertEquals("Geralt", encontrado.get().getNome().get());
        }

        @Test
        @DisplayName("buscarPorNome deve ser case-insensitive")
        void deveBuscarCaseInsensitive() {
            assertTrue(guilda.buscarPorNome("geralt").isPresent());
            assertTrue(guilda.buscarPorNome("GERALT").isPresent());
        }

        @Test
        @DisplayName("buscarPorNome deve retornar Optional vazio para caçador inexistente")
        void deveRetornarOptionalVazioParaInexistente() {
            assertTrue(guilda.buscarPorNome("Vesemir").isEmpty());
        }
    }

    // ─── Operações no caçador ─────────────────────────────────────────────────

    @Nested
    @DisplayName("Operações pós-registro no Cacador")
    class OperacoesPosRegistro {

        @Test
        @DisplayName("deve registrar missão concluída e manter lista imutável externamente")
        void deveRegistrarMissaoEMantterListaImutavel() {
            var resultado = guilda.registrar("Geralt", "Lobo", "G01-LOBO", "Kaer Morhen", "Fortaleza");
            var cacador   = resultado.getCacador();

            cacador.registrarMissaoConcluida("Caça ao Grifo");
            assertEquals(1, cacador.getMissoesConcluidas().size());

            assertThrows(UnsupportedOperationException.class,
                    () -> cacador.getMissoesConcluidas().add("Hack externo"));
        }

        @Test
        @DisplayName("caçador inativo não deve aceitar novas missões")
        void cacadorInativoNaoDeveAceitarMissoes() {
            var resultado = guilda.registrar("Lambert", "Lobo", "L03-LOBO", "Oxenfurt", "Taverna");
            var cacador   = resultado.getCacador();

            cacador.desativar();
            assertThrows(IllegalStateException.class,
                    () -> cacador.registrarMissaoConcluida("Missão qualquer"));
        }

        @Test
        @DisplayName("moverPara deve atualizar a localização do caçador")
        void moverParaDeveAtualizarLocalizacao() {
            var resultado = guilda.registrar("Eskel", "Lobo", "E05-LOBO", "Kaer Morhen", "Fortaleza");
            var cacador   = resultado.getCacador();

            cacador.moverPara("Toussaint", "Castelo Beauclair");
            var ficha = cacador.gerarFicha();
            assertTrue(ficha.localizacao.contains("Toussaint"));
        }

        @Test
        @DisplayName("gerarFicha deve retornar DTO com status ativo")
        void gerarFichaDeveRetornarStatusAtivo() {
            var resultado = guilda.registrar("Geralt", "Lobo", "G01-LOBO", "Kaer Morhen", "Fortaleza");
            var ficha     = resultado.getCacador().gerarFicha();
            assertTrue(ficha.statusAtivo.contains("ATIVO"));
        }
    }
}