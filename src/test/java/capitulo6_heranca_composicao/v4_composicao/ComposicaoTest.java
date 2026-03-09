package capitulo6_heranca_composicao.v4_composicao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("🧩 v4: A Cura pela Composição (Favor Composition over Inheritance)")
class ComposicaoTest {

    @Test
    @DisplayName("Conta Comum usa o ManipuladorDeSaldo e rende corretamente")
    void contaComumRendeCorretamente() {
        ContaComum conta = new ContaComum();
        conta.deposita(1000.0);
        conta.rende();
        assertEquals(1100.0, conta.getSaldo(), 0.001);
    }

    @Test
    @DisplayName("Conta de Estudante usa o ManipuladorDeSaldo mas NÃO tem método rende")
    void contaDeEstudanteDepositaESaca() {
        ContaDeEstudante conta = new ContaDeEstudante();
        conta.deposita(500.0);
        conta.saca(200.0);
        assertEquals(300.0, conta.getSaldo(), 0.001);
    }

    @Test
    @DisplayName("Garantia de Design: ContaDeEstudante não herda comportamento indesejado")
    void contaDeEstudanteNaoTemRende() throws NoSuchMethodException {
        Class<?> clazz = ContaDeEstudante.class;
        assertThrows(NoSuchMethodException.class,
                () -> clazz.getMethod("rende"),
                "ContaDeEstudante não deve ter método rende()");
    }

    @Test
    @DisplayName("ManipuladorDeSaldo é uma peça reutilizável e testável isoladamente")
    void manipuladorDeSaldoEhReutilizavel() {
        ManipuladorDeSaldo m = new ManipuladorDeSaldo();
        m.adiciona(1000.0);
        m.aplicaJuros(0.05);
        assertEquals(1050.0, m.getSaldo(), 0.001);
    }
}
