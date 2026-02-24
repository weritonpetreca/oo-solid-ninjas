package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 🚚 SERVIÇO DE ENTREGA (A ABSTRAÇÃO)
 *
 * Segunda abstração fundamental deste capítulo.
 *
 * ⚔️ O QUE ELA REPRESENTA:
 * "Exista alguma coisa que saiba calcular o frete para uma cidade."
 *
 * A {@link capitulo4_ocp.v3_calculadora_aberta.CalculadoraDePrecos} não sabe
 * se é Correios, Motoboy, Drone ou Teletransporte.
 * Ela apenas invoca {@code para(cidade)} e confia no contrato.
 *
 * 🛡️ OCP + DIP COMBINADOS:
 * - OCP: Novas formas de entrega não exigem alterar a Calculadora.
 * - DIP: A Calculadora depende desta abstração, nunca de Correios concretos.
 *
 * ⚔️ ANALOGIA WITCHER:
 * É o contrato de um "Transportador de Troféus".
 * Não importa se é um escudeiro, um corvo ou uma carroça.
 * Desde que o troféu chegue ao destino, o contrato foi cumprido.
 */

public interface ServicoDeEntrega {
    double para(String cidade);
}
