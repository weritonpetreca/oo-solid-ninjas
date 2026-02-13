package capitulo2_coesao.v4_a_mascara_isp;

/**
 * 🧪 ESTRATÉGIA: DEZ OU VINTE POR CENTO
 *
 * Implementação concreta da regra de negócio para cargos de alto nível.
 *
 * 🛡️ SEGURANÇA NA PRÁTICA:
 * Tente digitar 'dados.getPedidos()' aqui dentro. O compilador (IntelliJ) não deixará.
 * O programador júnior não consegue cometer o erro de acessar dados pesados,
 * porque a variável 'dados' é do tipo 'DadosParaCalculo', e não 'Funcionario'.
 */

public class DezOuVintePorCento implements RegraDeCalculo {

    @Override
    public double calcula(DadosParaCalculo dados) {
        if (dados.getSalarioBase() > 3000.0) {
            return dados.getSalarioBase() * 0.8;
        } else {
            return dados.getSalarioBase() * 0.9;
        }
    }
}