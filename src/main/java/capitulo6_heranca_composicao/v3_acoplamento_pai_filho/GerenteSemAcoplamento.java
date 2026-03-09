package capitulo6_heranca_composicao.v3_acoplamento_pai_filho;

/**
 * SOLUÇÃO v3: Gerente com implementação própria.
 *
 * Aniche, Cap. 6, seção 6.4:
 * "Gerentes ganham 30%, independente dos outros funcionários."
 *
 * ✅ Implementação própria: não depende do que o pai faz.
 * ✅ Se Funcionario.bonus() mudar: zero impacto aqui.
 * ✅ A regra do Gerente é explícita e isolada.
 *
 * Quando usar super.bonus() é válido?
 * Se a regra for: "bônus do gerente = bônus do funcionário + R$500"
 * Aí faz todo sentido: return super.bonus() + 500;
 * A dependência é semântica, não acidental.
 */
public class GerenteSemAcoplamento extends Funcionario{

    private String placaDoCarro;

    public GerenteSemAcoplamento(double salario, String placaDoCarro) {
        super(salario);
        this.placaDoCarro = placaDoCarro;
    }

    @Override
    public double bonus() {
        // ✅ Independente — própria implementação, própria regra
        return this.salario * 0.3; // 30% fixo, não depende do pai
    }

    public String getPlacaDoCarro() { return placaDoCarro; }
}
