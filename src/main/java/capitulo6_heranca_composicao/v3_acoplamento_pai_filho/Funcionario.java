package capitulo6_heranca_composicao.v3_acoplamento_pai_filho;

/**
 * Funcionário com cálculo de bônus.
 * Aniche, Cap. 6, seção 6.4.
 *
 * O atributo salario está como protected — isso permite que subclasses
 * o acessem diretamente. Aniche questiona: isso é bom?
 * Se a regra de alteração do salário tiver complexidade, protected é perigoso.
 */
public class Funcionario {
    // ⚠️ protected: subclasses podem acessar e modificar diretamente
    // Aniche sugere avaliar se isso é realmente necessário
    protected double salario;

    public Funcionario(double salario) { this.salario = salario; }

    public double bonus() {
        return this.salario * 0.2; // 20%
    }

    public double getSalario() { return salario; }
}
