package capitulo4_ocp;

import capitulo4_ocp.v3_calculadora_aberta.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


/**
 * 🧪 TESTES DO CAPÍTULO 4 — OCP E TESTABILIDADE
 *
 * Este arquivo demonstra UM DOS MAIORES BENEFÍCIOS do OCP:
 * classes abertas são fáceis de testar.
 *
 * ⚔️ A LIÇÃO DO LIVRO:
 * "A partir do momento em que a classe deixa clara todas as suas dependências,
 * e possibilita a troca delas, criamos classes não só facilmente extensíveis,
 * mas também altamente testáveis."
 *                                          — Maurício Aniche
 *
 * ⚔️ A RELAÇÃO TESTABILIDADE x DESIGN:
 * "Se está difícil de testar, é porque seu código pode ser melhorado."
 *
 * Compare os dois testes abaixo:
 * - {@code CalculadoraV1Test}: Difícil de testar — as dependências estão "soldadas"
 * - {@code CalculadoraV3Test}: Trivial — passamos mocks pelo construtor
 */

// =============================================================================
// TESTES DA V1 (O PROBLEMA)
// =============================================================================

/**
 * 🧪 TESTES DA CALCULADORA RÍGIDA (v1)
 *
 * Para testar a v1, somos FORÇADOS a testar junto com as dependências concretas.
 * Não há como isolar a lógica da Calculadora da lógica da TabelaDePrecoPadrao e do Frete.
 *
 * Isso significa:
 * 1. Se a TabelaDePrecoPadrao estiver errada, ESTE TESTE FALHA.
 *    Mas o erro pode ser da Calculadora ou da Tabela — não sabemos.
 * 2. Se quisermos testar apenas o comportamento da Calculadora,
 *    não conseguimos. Estamos sempre testando tudo junto.
 * 3. Se as dependências fossem infraestrutura real (banco de dados, HTTP),
 *    o teste seria impossível sem configurar todo o ambiente.
 *
 * ⚠️ SINTOMA: Setup mais complexo, menor controle sobre os cenários.
 */

@DisplayName("⚠️ Testes v1: A Calculadora Rígida (difícil de testar)")
class CalculadoraV1Test {

    @Test
    @DisplayName("Deve calcular com TabelaPadrao e Frete concretos")
    void deveCalcularComDependenciasConcretas() {

        // Sem controle sobre o comportamento das dependências
        capitulo4_ocp.v1_o_problema.CalculadoraDePrecos calc = new capitulo4_ocp.v1_o_problema.CalculadoraDePrecos();

        // Produto acima de 1000 (5% desconto) + SP (R$15 frete)
        // 3000 * (1 - 0.05) + 15 = 2850 + 15 = 2865
        capitulo4_ocp.v1_o_problema.Compra compra =
                new capitulo4_ocp.v1_o_problema.Compra("Espada de Prata", 3000.0, "São Paulo");
        double resultado = calc.calcula(compra);

        assertEquals(2865.0, resultado, 0.001);
    }
}

// =============================================================================
// TESTES DA V3 (A SOLUÇÃO OCP)
// =============================================================================

/**
 * 🧪 TESTES DA CALCULADORA ABERTA (v3) — A GLÓRIA DOS MOCKS
 *
 * Agora podemos testar a {@link CalculadoraDePrecos} isoladamente.
 * Passamos mocks das interfaces, e controlamos exatamente o comportamento esperado.
 *
 * 🛡️ VANTAGENS:
 * 1. Setup simples: 2 mocks de interfaces.
 * 2. Controle total: definimos exatamente o que cada dependência retorna.
 * 3. Se o teste falhar, a culpa é EXCLUSIVAMENTE da Calculadora.
 * 4. Não precisamos de banco, HTTP, ou qualquer infraestrutura real.
 *
 * ⚔️ CITAÇÃO DO LIVRO:
 * "Mock Objects é o nome que damos para objetos falsos, que simulam
 * o comportamento de outros objetos. Eles são especialmente úteis
 * durante a escrita de testes automatizados."
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("✅ Testes v3: A Calculadora Aberta (OCP — fácil de testar)")
class CalculadoraV3Test {

    @Mock
    private TabelaDePreco simuladorDeTabela;        // Interface - fácil de mockar

    @Mock
    private ServicoDeEntrega simuladorDeEntrega;    // Interface - fácil de mockar

    @Test
    @DisplayName("Deve calcular: valor - desconto + frete (exemplo do livro")
    void deveCalcularDescontoEFrete() {
        // 1. ARRANGE
        // Criamos a calculadora passando so mocks - O OCP tornando isso possível
        CalculadoraDePrecos calculadora = new CalculadoraDePrecos(simuladorDeTabela, simuladorDeEntrega);

        Compra cd = new Compra("CD do Jorge e Mateus", 50, "São Paulo");

        // Simulamos o comportamento das dependências
        // Exatamente como no livro
        when(simuladorDeTabela.descontoPara(50)).thenReturn(0.1);        // No livro está errado (5.0)
        when(simuladorDeEntrega.para("São Paulo")).thenReturn(10.0);    // Frete fixo

        // 2. ACT
        double valor = calculadora.calcula(cd);

        // 3. ASSERT
        assertEquals(55.0, valor, 0.001);
    }

    @Test
    @DisplayName("Deve retornar só o valor do produto quando desconto = 0 e frete = 0")
    void deveRetornarValorPuroSemDescontoOuFrete() {

        CalculadoraDePrecos calculadora = new CalculadoraDePrecos(simuladorDeTabela, simuladorDeEntrega);
        Compra compra = new Compra("Poção", 200.0, "Curitiba");

        when(simuladorDeTabela.descontoPara(200.0)).thenReturn(0.0);
        when(simuladorDeEntrega.para("Curitiba")).thenReturn(0.0);

        double resultado = calculadora.calcula(compra);

        assertEquals(200, resultado, 0.001);
    }

    @Test
    @DisplayName("Deve aplicar desconto de 10% e somar frete de R$30")
    void deveAplicarDescontoEFrete() {

        CalculadoraDePrecos calculadora = new CalculadoraDePrecos(simuladorDeTabela, simuladorDeEntrega);
        Compra compra = new Compra("Armadura", 1000.0, "Manaus");

        // 10% de desconto = 0.10 como fração
        when(simuladorDeTabela.descontoPara(1000.0)).thenReturn(0.1);
        when(simuladorDeEntrega.para("Manaus")).thenReturn(30.0);

        double resultado = calculadora.calcula(compra);

        // 1000 * (1 - 0.10) + 30 = 930
        assertEquals(930, resultado, 0.001);
    }

    @Test
    @DisplayName("Deve funcionar com TabelaSemDesconto e FreteGratis - OCP sem mocks")
    void deveFuncionarComImplementacoesReaisAlternativas() {

        // Demonstra que o OCP funciona mesmo sem mocks
        // Passamos implementações reais diferentes - sem modificar a Calculadora
        CalculadoraDePrecos calculadoraVip = new CalculadoraDePrecos(
                new TabelaSemDesconto(), // zero desconto
                new FreteGratis()        // zero frete
        );

        Compra compra = new Compra("Item VIP", 5000.0, "Qualquer cidade");
        double resultado = calculadoraVip.calcula(compra);

        // Sem desconto + sem frete = preço original
        assertEquals(5000.0, resultado, 0.001);
    }


}