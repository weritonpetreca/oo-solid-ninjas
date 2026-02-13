package capitulo2_coesao.v2_o_sinal_strategy;

import capitulo2_coesao.v1_a_maldicao_god_class.Funcionario;

/**
 * ⚔️ ÓLEO: QUINZE OU VINTE E CINCO POR CENTO
 *
 * Outra estratégia concreta.
 * Focada em regras para cargos como DBA e TESTER.
 *
 * 🧙‍♂️ NOTA DE DESIGN:
 * Perceba que o código é muito semelhante à outra regra.
 * Um iniciante tentaria criar uma classe "RegraGenerica" para reaproveitar código (Herança).
 * Um Mestre Bruxo sabe que *Duplicação de Código* é melhor que *Acoplamento Ruim*.
 * Mantenha separadas, pois as regras de negócio tendem a evoluir de formas diferentes.
 */
public class QuinzeOuVinteCincoPorCento implements RegraDeCalculo {

    @Override
    public double calcula(Funcionario funcionario) {
        if (funcionario.getSalarioBase() > 2000.0) {
            return funcionario.getSalarioBase() * 0.75;
        } else {
            return funcionario.getSalarioBase() * 0.85;
        }
    }
}