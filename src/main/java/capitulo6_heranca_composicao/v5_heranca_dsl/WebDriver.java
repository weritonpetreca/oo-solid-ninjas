package capitulo6_heranca_composicao.v5_heranca_dsl;

import java.util.HashMap;
import java.util.Map;

/**
 * Simulação didática do WebDriver do Selenium.
 * Em produção: org.openqa.selenium.WebDriver
 *
 * A API real é verbosa por design — é uma DSL de automação de browser.
 * O objetivo do capítulo é mostrar como herança pode ser usada para
 * criar uma camada mais legível sobre APIs verbosas.
 */
public class WebDriver {
    private final Map<String, String> campos = new HashMap<>();
    private String ultimoClique;

    public WebElement findElement(String id) { return new WebElement(id, this); }

    void setCampo(String id, String valor) { campos.put(id, valor); }
    void setClique(String id) { this.ultimoClique = id; }
    public String getConteudo(String id) { return campos.getOrDefault(id, ""); }
    public String getUltimoClique() { return ultimoClique; }
    public Map<String, String> getCampos() { return campos; }
}
