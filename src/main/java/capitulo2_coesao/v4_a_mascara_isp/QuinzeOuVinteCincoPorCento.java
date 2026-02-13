package capitulo2_coesao.v4_a_mascara_isp;

/**
 * ⚔️ ESTRATÉGIA: QUINZE OU VINTE E CINCO POR CENTO
 *
 * Implementação concreta para DBA e TESTER.
 * Segue o mesmo princípio de segurança da estratégia anterior.
 * Coesão alta: só muda se a matemática dessa regra mudar.
 */

public class QuinzeOuVinteCincoPorCento implements RegraDeCalculo {

    @Override
    public double calcula(DadosParaCalculo dados) {
        if (dados.getSalarioBase() > 2000.0) {
            return dados.getSalarioBase() * 0.75;
        } else {
            return dados.getSalarioBase() * 0.85;
        }
    }
}