package capitulo2_coesao.v3_o_elixir_enum;

/**
 * 👤 FUNCIONÁRIO (v3)
 *
 * O Funcionario desta versão está vinculado ao Cargo inteligente (v3).
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