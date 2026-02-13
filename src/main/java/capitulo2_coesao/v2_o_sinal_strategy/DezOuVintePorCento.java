package capitulo2_coesao.v2_o_sinal_strategy;

import capitulo2_coesao.v1_a_maldicao_god_class.Funcionario;

/**
 * 🧪 POÇÃO: DEZ OU VINTE POR CENTO
 *
 * Esta classe é uma estratégia concreta (Concrete Strategy).
 * Ela encapsula a lógica de negócio específica para cargos como DESENVOLVEDOR.
 *
 * 🛡️ SRP (Single Responsibility Principle):
 * Sua única razão de mudar é: "A alíquota para salários altos ou baixos mudou".
 * Ela não sabe o que é um DBA ou um Tester. Ela só sabe fazer contas.
 */
public class DezOuVintePorCento implements RegraDeCalculo {

    @Override
    public double calcula(Funcionario funcionario) {
        if (funcionario.getSalarioBase() > 3000.0) {
            return funcionario.getSalarioBase() * 0.8;
        } else {
            return funcionario.getSalarioBase() * 0.9;
        }
    }
}