package capitulo6_heranca_composicao.v3_acoplamento_pai_filho;

/**
 * PROBLEMA v3: Gerente acoplado à implementação do pai.
 *
 * Aniche, Cap. 6, seção 6.4:
 * "Apesar de funcionar, qualquer mudança na implementação do pai
 *  afetará diretamente o filho."
 *
 * Aqui, bonus() do Gerente chama super.bonus() e soma mais 10%.
 * Se o Funcionario mudar a fórmula de 20% para 15%,
 * o Gerente silenciosamente passa a calcular 15% + 10% = 25% (e não 30%).
 *
 * ❌ O filho conhece e depende dos detalhes internos do pai.
 * ❌ Uma mudança no pai propaga-se silenciosamente para o filho.
 */
public class GerenteComAcoplamento extends Funcionario {

    private String placaDoCarro;

    public GerenteComAcoplamento(double salario, String placaDoCarro) {
        super(salario);
        this.placaDoCarro = placaDoCarro;
    }

    @Override
    public double bonus() {
        // ❌ Depende da implementação do pai
        // Se Funcionario.bonus() mudar, este cálculo muda junto — silenciosamente
        return super.bonus() + this.salario * 0.1;
    }

    public String getPlacaDoCarro() { return placaDoCarro; }
}
