package capitulo7_interfaces_magras.v3_tributavel;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TributavelTest {

    @Test
    void calculaImpostoDeItensAbaixoDe1000() {
        NotaFiscal nf = new NotaFiscal("Geralt", "Kaer Morhen, 1");
        nf.adicionaItem(new Item("Espada de Prata", 800.0));
        nf.adicionaItem(new Item("Poção de golondrina", 200.0));

        CalculadorDeImposto calc = new CalculadorDeImposto();
        double imposto = calc.calcula(nf);

        assertEquals(10.0, imposto, 0.001); // (800 + 200) * 1%
    }

    @Test
    void calculaImpostoDeItensAcimaDe1000() {
        NotaFiscal nf = new NotaFiscal("Yennefer", "Vengerberg, 2");
        nf.adicionaItem(new Item("Grimório arcano", 5000.0));

        CalculadorDeImposto calc = new CalculadorDeImposto();
        double imposto = calc.calcula(nf);

        assertEquals(100.0, imposto, 0.001); // 5000 * 2%
    }

    @Test
    void calculadorFuncionaComQualquerTributavel() {
        Tributavel tributavelSimples = () -> List.of(
                new Item("Item A", 500.0),
                new Item("Item B", 1500.0)
        );

        CalculadorDeImposto calc = new CalculadorDeImposto();
        double imposto = calc.calcula(tributavelSimples);

        assertEquals(35, imposto, 0.001); // 500*1% + 1500*2%
    }
}
