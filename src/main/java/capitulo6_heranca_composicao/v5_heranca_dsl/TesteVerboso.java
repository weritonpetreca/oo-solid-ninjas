package capitulo6_heranca_composicao.v5_heranca_dsl;

/**
 * PROBLEMA v6: Teste sem DSL — API do Selenium usada diretamente.
 *
 * Aniche, Cap. 6, seção 6.6:
 * A API do Selenium é verbosa por natureza. Veja a repetição:
 *   driver.findElement(By.id("...")).sendKeys(...)
 *   driver.findElement(By.id("...")).click()
 *
 * Além de verboso, qualquer mudança na estrutura do teste
 * exige alterar cada linha individualmente.
 * E se tivermos 20 testes com a mesma estrutura?
 */
public class TesteVerboso {

    private WebDriver driver;

    public TesteVerboso(WebDriver driver) { this.driver = driver; }

    public void testaCadastroDeCacador() {
        // ❌ Verboso: driver.findElement + .sendKeys repetido em todo teste
        driver.findElement("nome").sendKeys("Geralt de Rivia");
        driver.findElement("escola").sendKeys("Escola do Lobo");
        driver.findElement("especialidade").sendKeys("Feitiços de Igni");
        driver.findElement("btnCadastrar").click();

        String resultado = driver.findElement("mensagem").getText();

        if (!resultado.equals("ok")) {
            throw new AssertionError("Esperado 'ok', mas foi: " + resultado);
        }
    }

    public void testaCadastroDeOutroCacador() {
        // ❌ Mesma verbosidade repetida — sem reúso possível
        driver.findElement("nome").sendKeys("Yennefer de Vengerberg");
        driver.findElement("escola").sendKeys("Escola de Aretuza");
        driver.findElement("especialidade").sendKeys("Magia de Fogo");
        driver.findElement("btnCadastrar").click();

        String resultado = driver.findElement("mensagem").getText();

        if (!resultado.equals("ok")) {
            throw new AssertionError("Esperado 'ok', mas foi: " + resultado);
        }
    }
}
