package capitulo6_heranca_composicao.v5_heranca_dsl;

public class WebElement {
    private final String id;
    private final WebDriver driver;

    WebElement(String id, WebDriver driver) {
        this.id = id;
        this.driver = driver;
    }

    public void sendKeys(String valor) { driver.setCampo(id, valor); }
    public void click() { driver.setClique(id); }
    public String getText() { return driver.getConteudo(id); }
}
