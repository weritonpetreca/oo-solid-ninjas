package capitulo6_heranca_composicao.v5_heranca_dsl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testa o comportamento da DSL — comparando verbosidade vs legibilidade.
 */
@DisplayName("📜 v5: Herança para o Bem (DSL de Testes)")
class DslTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new WebDriver();
        // Simula o servidor retornando "ok" após o submit
        driver.setCampo("mensagem", "ok");
    }

    @Test
    @DisplayName("Teste Verboso: Código repetitivo e difícil de ler")
    void testeVerbosoPreenche() {
        TesteVerboso teste = new TesteVerboso(driver);
        teste.testaCadastroDeCacador();

        assertEquals("Geralt de Rivia", driver.getCampos().get("nome"));
        assertEquals("Escola do Lobo", driver.getCampos().get("escola"));
        assertEquals("btnCadastrar", driver.getUltimoClique());
    }

    @Test
    @DisplayName("Teste com DSL: Código limpo e expressivo (Herança de Comportamento)")
    void testeDslPreenche() {
        TesteDeCadastroDeCacador teste = new TesteDeCadastroDeCacador(driver);
        String resultado = teste.testaCadastroComSucesso();

        assertEquals("ok", resultado);
        assertEquals("Geralt de Rivia", driver.getCampos().get("nome"));
        assertEquals("Escola do Lobo", driver.getCampos().get("escola"));
    }

    @Test
    @DisplayName("DSL encapsula a complexidade do WebDriver")
    void dslEncapsulaChamadasVerbosas() {
        TesteDeCadastroDeCacador teste = new TesteDeCadastroDeCacador(driver);
        teste.testaCadastroDeOutroCacador();

        assertEquals("Yennefer de Vengerberg", driver.getCampos().get("nome"));
        assertEquals("Aretuza", driver.getCampos().get("escola"));
    }

    @Test
    @DisplayName("Reuso de código: Herança permite criar novos testes sem duplicar lógica de infra")
    void demonstraReusoSemRepeticao() {
        // Ambos os testes herdam preenche/submete/conteudo sem repetir
        // Se a API do Selenium mudar, muda só em TesteComDsl
        TesteDeCadastroDeCacador teste = new TesteDeCadastroDeCacador(driver);

        teste.testaCadastroComSucesso();
        String nomeGeralt = driver.getCampos().get("nome");

        driver = new WebDriver();
        driver.setCampo("mensagem", "ok");
        teste = new TesteDeCadastroDeCacador(driver);
        teste.testaCadastroDeOutroCacador();
        String nomeYen = driver.getCampos().get("nome");

        assertEquals("Geralt de Rivia", nomeGeralt);
        assertEquals("Yennefer de Vengerberg", nomeYen);
    }
}
