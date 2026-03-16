package capitulo8_consistencia.v2_validacao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap8 v2 — Validação")
class ValidacaoTest {

    // ─── CredencialDeBruxo (construtor) ──────────────────────────────────────

    @Nested
    @DisplayName("CredencialDeBruxo — validação no construtor")
    class CredencialDeBruxoTest {

        @Test
        @DisplayName("deve criar credencial com formato válido")
        void deveCriarCredencialValida() {
            var cred = new CredencialDeBruxo("G01-LOBO");
            assertEquals("G01-LOBO", cred.getCodigo());
        }

        @Test
        @DisplayName("deve aceitar múltiplas letras na escola")
        void deveAceitarEscolaComMultiplasLetras() {
            assertDoesNotThrow(() -> new CredencialDeBruxo("Y22-ARETUZA"));
        }

        @Test
        @DisplayName("deve rejeitar credencial nula")
        void deveRejeitarNula() {
            assertThrows(IllegalArgumentException.class, () -> new CredencialDeBruxo(null));
        }

        @Test
        @DisplayName("deve rejeitar credencial com letra minúscula")
        void deveRejeitarLetraMinuscula() {
            assertThrows(IllegalArgumentException.class, () -> new CredencialDeBruxo("g01-LOBO"));
        }

        @Test
        @DisplayName("deve rejeitar credencial com número de 3 dígitos")
        void deveRejeitarNumeroDeTresDigitos() {
            assertThrows(IllegalArgumentException.class, () -> new CredencialDeBruxo("G001-LOBO"));
        }

        @Test
        @DisplayName("deve rejeitar credencial sem traço")
        void deveRejeitarSemTraco() {
            assertThrows(IllegalArgumentException.class, () -> new CredencialDeBruxo("G01LOBO"));
        }

        @Test
        @DisplayName("deve rejeitar string vazia")
        void deveRejeitarStringVazia() {
            assertThrows(IllegalArgumentException.class, () -> new CredencialDeBruxo(""));
        }
    }

    // ─── CredencialBuilder ────────────────────────────────────────────────────

    @Nested
    @DisplayName("CredencialBuilder — resultado rico")
    class CredencialBuilderTest {

        @Test
        @DisplayName("deve construir credencial válida com sucesso")
        void deveConstruirComSucesso() {
            var resultado = new CredencialBuilder()
                    .comLetra("G").comNumero("01").comEscola("LOBO").build();

            assertTrue(resultado.isSucesso());
            assertNotNull(resultado.getCredencial());
            assertEquals("G01-LOBO", resultado.getCredencial().getCodigo());
        }

        @Test
        @DisplayName("deve retornar falha com erros descritivos para letra inválida")
        void deveRetornarFalhaParaLetraInvalida() {
            var resultado = new CredencialBuilder()
                    .comLetra("x")   // minúscula
                    .comNumero("01").comEscola("LOBO").build();

            assertFalse(resultado.isSucesso());
            assertFalse(resultado.getErros().isEmpty());
        }

        @Test
        @DisplayName("deve retornar falha com erros descritivos para número inválido")
        void deveRetornarFalhaParaNumeroInvalido() {
            var resultado = new CredencialBuilder()
                    .comLetra("G").comNumero("999").comEscola("LOBO").build();

            assertFalse(resultado.isSucesso());
            assertFalse(resultado.getErros().isEmpty());
        }

        @Test
        @DisplayName("deve acumular múltiplos erros em uma única chamada")
        void deveAcumularMultiplosErros() {
            var resultado = new CredencialBuilder()
                    .comLetra("x").comNumero("999").build(); // sem escola

            assertFalse(resultado.isSucesso());
            assertTrue(resultado.getErros().size() >= 2);
        }

        @Test
        @DisplayName("resultado de sucesso não deve ter erros")
        void resultadoDeSucessoNaoDeveTermErros() {
            var resultado = new CredencialBuilder()
                    .comLetra("C").comNumero("99").comEscola("LOBO").build();

            assertTrue(resultado.isSucesso());
            assertTrue(resultado.getErros().isEmpty());
        }
    }

    // ─── ValidadorComposto ────────────────────────────────────────────────────

    @Nested
    @DisplayName("ValidadorComposto — chain of responsibility")
    class ValidadorCompostoTest {

        private final ValidacaoDeCacador validador = new ValidadorComposto(List.of(
                new NomeRequerido(),
                new EscolaRequerida(),
                new NivelMinimo(10),
                new EmailValido()
        ));

        @Test
        @DisplayName("deve retornar lista vazia para caçador válido")
        void deveRetornarSemErrosParaCacadorValido() {
            var cacador = new CacadorParaRegistro("Geralt", "Lobo", 50, "geralt@lobo.com", "G01-LOBO");
            assertTrue(validador.validar(cacador).isEmpty());
        }

        @Test
        @DisplayName("deve detectar nome vazio")
        void deveDetectarNomeVazio() {
            var cacador = new CacadorParaRegistro("", "Lobo", 50, "geralt@lobo.com", "G01-LOBO");
            assertFalse(validador.validar(cacador).isEmpty());
        }

        @Test
        @DisplayName("deve detectar nível abaixo do mínimo")
        void deveDetectarNivelBaixo() {
            var cacador = new CacadorParaRegistro("Geralt", "Lobo", 5, "geralt@lobo.com", "G01-LOBO");
            var erros = validador.validar(cacador);
            assertFalse(erros.isEmpty());
            assertTrue(erros.stream().anyMatch(e -> e.contains("mínimo")));
        }

        @Test
        @DisplayName("deve detectar e-mail inválido")
        void deveDetectarEmailInvalido() {
            var cacador = new CacadorParaRegistro("Geralt", "Lobo", 50, "semArroba", "G01-LOBO");
            assertFalse(validador.validar(cacador).isEmpty());
        }

        @Test
        @DisplayName("deve acumular todos os erros em uma única chamada")
        void deveAcumularTodosOsErros() {
            var cacador = new CacadorParaRegistro("", "", 3, "invalido", "");
            var erros = validador.validar(cacador);
            assertTrue(erros.size() >= 3, "Esperado pelo menos 3 erros, mas obteve: " + erros.size());
        }
    }
}
