package capitulo8_consistencia.v6_imutabilidade;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap8 v6 — Imutabilidade")
class ImutabilidadeTest {

    @Nested
    @DisplayName("LocalizacaoImutavel")
    class LocalizacaoImutavelTest {

        @Test
        @DisplayName("deve criar localização com atributos corretos")
        void deveCriarLocalizacaoCorreta() {
            var loc = new LocalizacaoImutavel("Kaer Morhen", 52.0, 21.0);
            assertEquals("Kaer Morhen", loc.getRegiao());
            assertEquals(52.0, loc.getLatitude(), 0.001);
            assertEquals(21.0, loc.getLongitude(), 0.001);
        }

        @Test
        @DisplayName("comNovaRegiao deve retornar nova instância mantendo a original intacta")
        void comNovaRegiaoDeveRetornarNovaInstancia() {
            var original = new LocalizacaoImutavel("Kaer Morhen", 52.0, 21.0);
            var nova     = original.comNovaRegiao("Novigrad");

            // ✅ original intacta
            assertEquals("Kaer Morhen", original.getRegiao());
            // ✅ nova instância com nova região
            assertEquals("Novigrad", nova.getRegiao());
            // ✅ coordenadas preservadas
            assertEquals(original.getLatitude(),  nova.getLatitude(),  0.001);
            assertEquals(original.getLongitude(), nova.getLongitude(), 0.001);
            // ✅ são objetos diferentes
            assertNotSame(original, nova);
        }

        @Test
        @DisplayName("comNovasCoordenadas deve retornar nova instância mantendo a região")
        void comNovasCoordenadasDeveRetornarNovaInstancia() {
            var original = new LocalizacaoImutavel("Novigrad", 52.0, 21.0);
            var nova     = original.comNovasCoordenadas(51.5, 21.5);

            assertEquals("Novigrad", nova.getRegiao());
            assertEquals(51.5, nova.getLatitude(),  0.001);
            assertEquals(21.5, nova.getLongitude(), 0.001);
            assertNotSame(original, nova);
        }

        @Test
        @DisplayName("calcularDistanciaAte deve retornar valor positivo entre localizações diferentes")
        void calcularDistanciaDeveRetornarValorPositivo() {
            var a = new LocalizacaoImutavel("Kaer Morhen", 52.0, 21.0);
            var b = new LocalizacaoImutavel("Novigrad",    50.0, 19.0);
            assertTrue(a.calcularDistanciaAte(b) > 0);
        }

        @Test
        @DisplayName("distância de uma localização para ela mesma deve ser zero")
        void distanciaParaMesmaPontoDeveSerZero() {
            var loc = new LocalizacaoImutavel("Oxenfurt", 50.0, 20.0);
            assertEquals(0.0, loc.calcularDistanciaAte(loc), 0.001);
        }

        @Test
        @DisplayName("não deve criar localização com região nula")
        void naoDeveCriarComRegiaoNula() {
            assertThrows(IllegalArgumentException.class,
                    () -> new LocalizacaoImutavel(null, 0.0, 0.0));
        }

        @Test
        @DisplayName("não deve criar localização com região em branco")
        void naoDeveCriarComRegiaoEmBranco() {
            assertThrows(IllegalArgumentException.class,
                    () -> new LocalizacaoImutavel("  ", 0.0, 0.0));
        }
    }

    @Nested
    @DisplayName("RegistroDeRastro — encapsulamento de lista interna")
    class RegistroDeRastroTest {

        @Test
        @DisplayName("deve registrar avistamentos corretamente")
        void deveRegistrarAvistamentos() {
            var rastro = new RegistroDeRastro("Basilisco");
            rastro.adicionarAvistamento(new LocalizacaoImutavel("Velen", 50.0, 20.0));
            rastro.adicionarAvistamento(new LocalizacaoImutavel("Novigrad", 52.0, 21.0));

            assertEquals(2, rastro.totalAvistamentos());
        }

        @Test
        @DisplayName("getAvistamentos deve retornar lista imutável")
        void getAvistamentosDeveRetornarListaImutavel() {
            var rastro = new RegistroDeRastro("Wyvern");
            rastro.adicionarAvistamento(new LocalizacaoImutavel("Skellige", 55.0, 18.0));

            var lista = rastro.getAvistamentos();
            assertThrows(UnsupportedOperationException.class,
                    () -> lista.add(new LocalizacaoImutavel("Cintra", 51.0, 22.0)));
        }

        @Test
        @DisplayName("ultimoAvistamento deve retornar o último registrado")
        void ultimoAvistamentoDeveRetornarUltimo() {
            var rastro   = new RegistroDeRastro("Strige");
            var primeiro = new LocalizacaoImutavel("Velen",   50.0, 20.0);
            var ultimo   = new LocalizacaoImutavel("Novigrad", 52.0, 21.0);

            rastro.adicionarAvistamento(primeiro);
            rastro.adicionarAvistamento(ultimo);

            assertEquals("Novigrad", rastro.ultimoAvistamento().getRegiao());
        }

        @Test
        @DisplayName("ultimoAvistamento deve lançar exceção se não há avistamentos")
        void ultimoAvistamentoDeveLancarExcecaoSemAvistamentos() {
            var rastro = new RegistroDeRastro("Fantasma");
            assertThrows(IllegalStateException.class, rastro::ultimoAvistamento);
        }

        @Test
        @DisplayName("não deve aceitar avistamento nulo")
        void naoDeveAceitarAvistamentoNulo() {
            var rastro = new RegistroDeRastro("Espectro");
            assertThrows(IllegalArgumentException.class,
                    () -> rastro.adicionarAvistamento(null));
        }
    }
}