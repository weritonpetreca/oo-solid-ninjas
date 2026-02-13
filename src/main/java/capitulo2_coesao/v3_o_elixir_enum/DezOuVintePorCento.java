package capitulo2_coesao.v3_o_elixir_enum;

/**
 * 🧪 ESTRATÉGIA CONCRETA (v3)
 *
 * A lógica matemática é a mesma da v2, mas agora ela implementa a interface
 * local deste pacote.
 *
 * 🧙‍♂️ O QUE MUDOU?
 * O comportamento não mudou. O que mudou foi COMO chegamos aqui.
 * Antes (v2), a Calculadora instanciava esta classe.
 * Agora (v3), o ENUM Cargo vai instanciar esta classe.
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