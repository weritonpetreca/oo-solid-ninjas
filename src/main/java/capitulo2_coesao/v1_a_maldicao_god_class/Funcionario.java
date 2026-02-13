package capitulo2_coesao.v1_a_maldicao_god_class;

/**
 * 👤 FUNCIONÁRIO (ESTRUTURA DE DADOS)
 *
 * Este é um POJO (Plain Old Java Object) clássico.
 *
 * 🛡️ ANÁLISE DO BRUXO:
 * Nesta arquitetura v1, esta classe é apenas uma "bolsa de dados".
 * Ela expõe seus atributos vitais (salário, cargo) através de Getters,
 * permitindo que a CalculadoraDeSalario (a God Class) tome todas as decisões.
 *
 * Isso viola o princípio "Tell, Don't Ask" (Diga, não pergunte).
 * Em vez de pedirmos os dados ao funcionário para calcular fora,
 * deveríamos dizer ao funcionário (ou à sua estratégia): "Calcule-se".
 */
public class Funcionario {
    private Cargo cargo;
    private double salarioBase;

    public Funcionario(Cargo cargo, double salarioBase) {
        this.cargo = cargo;
        this.salarioBase = salarioBase;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public double getSalarioBase() {
        return salarioBase;
    }
}