package capitulo2_coesao.v3_o_elixir_enum;

/**
 * ⚔️ ESTRATÉGIA CONCRETA (v3)
 *
 * Responsável pelas regras de DBA e TESTER neste novo contexto.
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