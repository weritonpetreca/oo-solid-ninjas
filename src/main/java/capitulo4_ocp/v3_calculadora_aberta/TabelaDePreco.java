package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 📜 TABELA DE PREÇO (A ABSTRAÇÃO)
 *
 * Esta interface é o coração do OCP neste capítulo.
 *
 * ⚔️ O QUE ELA REPRESENTA:
 * "Exista alguma coisa que saiba calcular um desconto dado um valor."
 *
 * A {@link capitulo4_ocp.v3_calculadora_aberta.CalculadoraDePrecos} não
 * sabe — nem quer saber — se é uma tabela padrão, VIP ou promocional.
 * Ela apenas invoca {@code descontoPara(valor)} e confia no contrato.
 *
 * 🛡️ POR QUE ISSO RESOLVE O OCP?
 * Quer criar uma nova tabela de preços?
 * - ✅ Crie uma nova classe que implemente esta interface.
 * - ✅ A {@link capitulo4_ocp.v3_calculadora_aberta.CalculadoraDePrecos} NÃO precisa mudar.
 * - ✅ Os testes existentes continuam funcionando.
 *
 * Isso é o princípio do Aberto-Fechado em ação:
 * Aberto para extensão (novas tabelas), fechado para modificação (a calculadora).
 *
 * 💡 ESTABILIDADE:
 * Esta interface tende a ser ESTÁVEL — ela muda muito pouco.
 * O que muda são as implementações, não o contrato.
 */

public interface TabelaDePreco {
    double descontoPara(double valor);
}
