package capitulo2_coesao.v2_o_sinal_strategy;

import capitulo2_coesao.v1_a_maldicao_god_class.Funcionario;

/**
 * 📜 O CONTRATO (INTERFACE)
 *
 * No mundo dos Bruxos, um contrato é sagrado.
 * Esta interface define a única responsabilidade que uma classe de regra deve ter:
 * calcular o salário dado um funcionário.
 *
 * ⚔️ VANTAGEM TÁTICA:
 * Desacoplamento. Quem usa esta interface não precisa saber QUAL regra está sendo
 * executada, apenas que o resultado será um número (double).
 */
public interface RegraDeCalculo {
    double calcula(Funcionario funcionario);
}