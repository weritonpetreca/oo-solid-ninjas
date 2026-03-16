package capitulo8_consistencia.v4_tiny_types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap8 v4 — Tiny Types")
class TinyTypesTest {

    @Nested
    @DisplayName("NomeDeGuerra")
    class NomeDeGuerraTest {

        @Test @DisplayName("deve aceitar nome válido")
        void deveAceitarNomeValido() {
            var nome = new NomeDeGuerra("Geralt de Rívia");
            assertEquals("Geralt de Rívia", nome.get());
        }

        @Test @DisplayName("deve rejeitar nome nulo")
        void deveRejeitarNulo() {
            assertThrows(IllegalArgumentException.class, () -> new NomeDeGuerra(null));
        }

        @Test @DisplayName("deve rejeitar nome em branco")
        void deveRejeitarEmBranco() {
            assertThrows(IllegalArgumentException.class, () -> new NomeDeGuerra("   "));
        }

        @Test @DisplayName("deve fazer trim do valor")
        void deveFazerTrim() {
            var nome = new NomeDeGuerra("  Geralt  ");
            assertEquals("Geralt", nome.get());
        }
    }

    @Nested
    @DisplayName("NivelDeExperiencia")
    class NivelDeExperienciaTest {

        @Test @DisplayName("deve aceitar nível entre 1 e 100")
        void deveAceitarNivelValido() {
            assertDoesNotThrow(() -> new NivelDeExperiencia(50));
        }

        @Test @DisplayName("deve aceitar nível 1 (mínimo)")
        void deveAceitarNivelMinimo() {
            assertDoesNotThrow(() -> new NivelDeExperiencia(1));
        }

        @Test @DisplayName("deve aceitar nível 100 (máximo)")
        void deveAceitarNivelMaximo() {
            assertDoesNotThrow(() -> new NivelDeExperiencia(100));
        }

        @Test @DisplayName("deve rejeitar nível 0")
        void deveRejeitarNivelZero() {
            assertThrows(IllegalArgumentException.class, () -> new NivelDeExperiencia(0));
        }

        @Test @DisplayName("deve rejeitar nível 101")
        void deveRejeitarNivelAcimaDoMaximo() {
            assertThrows(IllegalArgumentException.class, () -> new NivelDeExperiencia(101));
        }

        @Test @DisplayName("isVeterano deve retornar true para nível >= 70")
        void isVeteranoDeveSerTrueAcimaDe70() {
            assertTrue(new NivelDeExperiencia(70).isVeterano());
            assertTrue(new NivelDeExperiencia(99).isVeterano());
        }

        @Test @DisplayName("isVeterano deve retornar false para nível < 70")
        void isVeteranoDeveSerFalseAbaixoDe70() {
            assertFalse(new NivelDeExperiencia(69).isVeterano());
        }

        @Test @DisplayName("isLendario deve retornar true para nível >= 90")
        void isLendarioDeveSerTrueAcimaDe90() {
            assertTrue(new NivelDeExperiencia(90).isLendario());
            assertTrue(new NivelDeExperiencia(100).isLendario());
        }
    }

    @Nested
    @DisplayName("EmailDeCacador")
    class EmailDeCacadorTest {

        @Test @DisplayName("deve aceitar e-mail válido")
        void deveAceitarEmailValido() {
            var email = new EmailDeCacador("geralt@lobo.com");
            assertEquals("geralt@lobo.com", email.get());
        }

        @Test @DisplayName("deve normalizar e-mail para minúsculas")
        void deveNormalizarParaMinusculas() {
            var email = new EmailDeCacador("GERALT@LOBO.COM");
            assertEquals("geralt@lobo.com", email.get());
        }

        @Test @DisplayName("deve rejeitar e-mail sem @")
        void deveRejeitarEmailSemArroba() {
            assertThrows(IllegalArgumentException.class, () -> new EmailDeCacador("semArroba"));
        }

        @Test @DisplayName("deve rejeitar e-mail nulo")
        void deveRejeitarNulo() {
            assertThrows(IllegalArgumentException.class, () -> new EmailDeCacador(null));
        }
    }

    @Nested
    @DisplayName("CacadorComTinyTypes")
    class CacadorComTinyTypesTest {

        @Test @DisplayName("deve criar caçador com todos os tiny types válidos")
        void deveCriarComTinyTypesValidos() {
            var geralt = new CacadorComTinyTypes(
                    new NomeDeGuerra("Geralt"),
                    new EscolaDeCaca("Lobo"),
                    new EmailDeCacador("geralt@lobo.com"),
                    new NivelDeExperiencia(85)
            );
            assertEquals("Geralt", geralt.getNome().get());
            assertEquals("Lobo",   geralt.getEscola().get());
            assertTrue(geralt.isVeterano());
            assertFalse(geralt.isLendario());
        }

        @Test @DisplayName("deve indicar lendário para nível >= 90")
        void deveIndicarLendario() {
            var cacador = new CacadorComTinyTypes(
                    new NomeDeGuerra("Ciri"),
                    new EscolaDeCaca("Lobo"),
                    new EmailDeCacador("ciri@toussaint.com"),
                    new NivelDeExperiencia(95)
            );
            assertTrue(cacador.isLendario());
        }
    }
}
