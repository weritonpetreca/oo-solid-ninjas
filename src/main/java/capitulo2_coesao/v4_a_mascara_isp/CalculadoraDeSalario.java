package capitulo2_coesao.v4_a_mascara_isp;

/**
 * 🐺 GERALT (A CALCULADORA BLINDADA)
 *
 * A classe final que orquestra tudo.
 *
 * ⚔️ FLUXO DE COMBATE:
 * 1. Recebe o 'Funcionario' (Concreto).
 * 2. Pede ao Cargo do funcionário qual é a Regra.
 * 3. Executa a regra passando o funcionário.
 *
 * A MÁGICA DO JAVA:
 * Embora passemos o objeto 'Funcionario' na linha '...calcula(funcionario)',
 * o método 'calcula' lá na regra espera 'DadosParaCalculo'.
 * Como 'Funcionario' implementa essa interface, a passagem é automática e segura.
 * O objeto entra na regra vestindo a máscara, perdendo acesso aos métodos perigosos.
 */

public class CalculadoraDeSalario {

    public double calcula(Funcionario funcionario) {
        return funcionario.getCargo().getRegra().calcula(funcionario);
    }
}