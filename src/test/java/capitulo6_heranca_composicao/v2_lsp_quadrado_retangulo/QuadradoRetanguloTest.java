package capitulo6_heranca_composicao.v2_lsp_quadrado_retangulo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("📐 v2: O Dilema Geométrico (Quadrado é Retângulo?)")
class QuadradoRetanguloTest {

    @Test
    @DisplayName("Retângulo deve permitir lados diferentes (Contrato Base)")
    void retanguloComLadosDiferentes() {
        Retangulo r = new Retangulo(4, 6);
        assertEquals(24, r.area());
    }

    @Test
    @DisplayName("Quadrado força lados iguais (Comportamento Específico)")
    void quadradoSempreTemLadosIguais() {
        Quadrado q = new Quadrado(5);
        assertEquals(5, q.getX());
        assertEquals(5, q.getY()); // sempre igual ao X
        assertEquals(25, q.area());
    }

    @Test
    @DisplayName("Violação do LSP: Quadrado não cumpre o contrato do Retângulo")
    void demonstraViolacaoDaPreCondicao() {
        // Retangulo permite lados diferentes — esse é o contrato
        Retangulo r1 = new Retangulo(3, 7);
        assertEquals(21, r1.area());

        // Quadrado "é um Retangulo" mas força lados iguais
        // Quem espera um Retangulo e recebe um Quadrado
        // não consegue representar lados diferentes
        Retangulo r2 = new Quadrado(5); // polimorfismo aceita
        // r2 deveria aceitar (3, 7) mas Quadrado não tem esse construtor
        // Pré-condição mais forte → LSP violado
        assertEquals(r2.getX(), r2.getY(), "Quadrado sempre tem lados iguais — contrato diferente do Retângulo");
    }
}
