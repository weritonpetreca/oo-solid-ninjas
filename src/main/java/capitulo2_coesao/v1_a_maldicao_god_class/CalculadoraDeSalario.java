package capitulo2_coesao.v1_a_maldicao_god_class;

import static capitulo2_coesao.v1_a_maldicao_god_class.Cargo.*;

/**
 * 🧟‍♂️ A STRIGA (CLASSE NÃO COESA)
 *
 * No bestiário do código, esta classe representa uma violação clássica de Coesão.
 * Ela é uma "God Class" em treinamento: ela sabe demais e faz demais.
 *
 * ⚔️ SINAIS DA MALDIÇÃO (PROBLEMAS):
 * 1. Viola o SRP (Single Responsibility Principle):
 * - Ela muda se a regra de cálculo do DESENVOLVEDOR mudar.
 * - Ela muda se a regra do DBA mudar.
 * - Ela muda se um NOVO CARGO for adicionado.
 *
 * 2. Viola o OCP (Open/Closed Principle):
 * - A classe não está "fechada para modificação". A cada nova regra,
 * precisamos abrir o peito desta classe e cirurgicamente inserir mais IFs.
 *
 * 3. Dificuldade de Reúso:
 * - As regras (dezOuVintePorcento) estão presas como métodos privados.
 * Outras classes não podem usar essas poções (lógicas).
 */
public class CalculadoraDeSalario {

    public double calcula(Funcionario funcionario) {
        // ⚠️ O IF DO CAOS:
        // A classe precisa conhecer explicitamente todos os cargos do Continente.
        // Isso cria um acoplamento forte e uma cadeia de dependências frágil.
        if (DESENVOLVEDOR.equals(funcionario.getCargo())) {
            return dezOuVintePorcento(funcionario);
        }

        if (DBA.equals(funcionario.getCargo()) || TESTER.equals(funcionario.getCargo())) {
            return quinzeOuVinteCincoPorcento(funcionario);
        }

        throw new RuntimeException("Funcionário inválido ou Cargo desconhecido");
    }

    // 🔒 Regra aprisionada (Método Privado)
    // Lógica: Salário > 3000 recebe 80% (desconto de 20%), senão 90%.
    private double dezOuVintePorcento(Funcionario funcionario) {
        if (funcionario.getSalarioBase() > 3000.0) {
            return funcionario.getSalarioBase() * 0.8;
        } else {
            return funcionario.getSalarioBase() * 0.9;
        }
    }

    // 🔒 Regra aprisionada (Método Privado)
    // Lógica: Salário > 2000 recebe 75% (desconto de 25%), senão 85%.
    private double quinzeOuVinteCincoPorcento(Funcionario funcionario) {
        if (funcionario.getSalarioBase() > 2000.0) {
            return funcionario.getSalarioBase() * 0.75;
        } else {
            return funcionario.getSalarioBase() * 0.85;
        }
    }
}