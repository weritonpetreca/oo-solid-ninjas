package capitulo2_coesao.v2_o_sinal_strategy;

import capitulo2_coesao.v1_a_maldicao_god_class.Funcionario;

import static capitulo2_coesao.v1_a_maldicao_god_class.Cargo.*;

/**
 * 🐺 GERALT (EM TREINAMENTO)
 *
 * A Calculadora agora está mais limpa. Ela não faz mais as contas (não suja as mãos).
 * Ela delega a responsabilidade para as classes especialistas (DezOuVinte...).
 *
 * ⚠️ AINDA HÁ UMA FRAQUEZA:
 * Ela ainda precisa decidir QUAL estratégia usar baseada no Cargo.
 * Os IFs continuam aqui. Se surgir um novo cargo, teremos que mexer nesta classe.
 *
 * Estamos quase lá. O próximo passo (v3) eliminará estes IFs.
 */

public class CalculadoraDeSalario {

    public double calcula(Funcionario funcionario) {
        if (DESENVOLVEDOR.equals(funcionario.getCargo())) {
            return new DezOuVintePorCento().calcula(funcionario);
        }

        if (DBA.equals(funcionario.getCargo()) || TESTER.equals(funcionario.getCargo())) {
            return new QuinzeOuVinteCincoPorCento().calcula(funcionario);
        }

        throw new RuntimeException("Cargo não suportado");
    }
}
