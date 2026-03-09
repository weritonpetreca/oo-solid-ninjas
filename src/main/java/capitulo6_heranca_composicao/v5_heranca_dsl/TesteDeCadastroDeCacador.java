package capitulo6_heranca_composicao.v5_heranca_dsl;

/**
 * SOLUÇÃO v6: Teste limpo usando a DSL herdada de TesteComDsl.
 *
 * Aniche, Cap. 6, seção 6.6:
 * Compare com TesteVerboso:
 *
 *   ❌ Verboso:  driver.findElement("nome").sendKeys("Geralt de Rívia");
 *   ✅ Com DSL:  preenche("nome", "Geralt de Rívia");
 *
 * O código expressa INTENÇÃO, não mecânica.
 * Qualquer pessoa lê e entende o que o teste está fazendo.
 *
 * Importante: TesteDeCadastroDeCacador "é um" TesteComDsl?
 * Semanticamente, sim — é um tipo de teste com Selenium.
 * Aniche reconhece que às vezes fazemos "má herança" para ganhar clareza.
 * O trade-off é consciente.
 */
public class TesteDeCadastroDeCacador extends TesteComDsl {

    public TesteDeCadastroDeCacador(WebDriver driver) { super(driver); }

    public String testaCadastroComSucesso() {
        // ✅ Legível: intenção clara, sem ruído da API do Selenium
        preenche("nome", "Geralt de Rivia");
        preenche("escola", "Escola do Lobo");
        preenche("especialidade", "Feitiços de Igni");
        submete("btnCadastrar");

        return conteudo("mensagem");
    }

    public String testaCadastroDeOutroCacador() {
        preenche("nome", "Yennefer de Vengerberg");
        preenche("escola", "Aretuza");
        preenche("especialidade", "Magia de Fogo");
        submete("btnCadastrar");

        return conteudo("mensagem");
    }


}
