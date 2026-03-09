package capitulo6_heranca_composicao.v5_heranca_dsl;

/**
 * SOLUÇÃO v6: Classe base que cria uma DSL sobre a API do Selenium.
 *
 * Aniche, Cap. 6, seção 6.6:
 * Herança usada para FACILITAR A ESCRITA de classes filhas — uma DSL interna.
 *
 * Esse é um uso legítimo de herança mesmo sem a relação "X é um Y" pura:
 * "Às vezes abrimos mão de boas práticas para termos outros ganhos."
 *
 * O ganho aqui é clareza e reúso de métodos auxiliares em todos os testes.
 * A classe filha (TesteDeCadastro) herda preenche(), submete(), conteudo()
 * e pode escrever testes muito mais legíveis.
 *
 * Contexto real: PageObject Pattern em Selenium, BaseTest em JUnit,
 * AbstractIntegrationTest em Spring Boot — todos usam esse mesmo princípio.
 */
public abstract class TesteComDsl {

    protected WebDriver driver;

    public TesteComDsl(WebDriver driver) { this.driver = driver; }

    /**
     * Preenche um campo de formulário pelo id.
     * Encapsula: driver.findElement(By.id(id)).sendKeys(valor)
     */
    protected void preenche(String id, String valor) { driver.findElement(id).sendKeys(valor); }

    /**
     * Clica em um botão ou elemento pelo id.
     * Encapsula: driver.findElement(By.id(id)).click()
     */
    protected void submete(String id) { driver.findElement(id).click(); }

    /**
     * Retorna o texto de um elemento pelo id.
     * Encapsula: driver.findElement(By.id(id)).getText()
     */
    protected String conteudo(String id) { return driver.findElement(id).getText(); }
}
