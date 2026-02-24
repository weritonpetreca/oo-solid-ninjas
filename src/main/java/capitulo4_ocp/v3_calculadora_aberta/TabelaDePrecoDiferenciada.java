package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 💰 TABELA DE PREÇO DIFERENCIADA
 *
 * Uma nova regra de desconto criada SEM MODIFICAR a {@link CalculadoraDePrecos}.
 * Basta criar esta nova classe e passá-la pelo construtor.
 *
 * ⚔️ ISSO É O OCP EM AÇÃO:
 * Extendemos o sistema com uma nova regra de negócio
 * sem tocar em uma linha da classe principal.
 *
 * Regras:
 * - Acima de R$ 5.000: 6% de desconto
 * - Acima de R$ 1.000: 8% de desconto
 * - Demais: 1% (parceiros sempre têm algo)
 */

public class TabelaDePrecoDiferenciada implements TabelaDePreco {
    @Override
    public double descontoPara(double valor) {
        if (valor > 5000) return 0.06;
        if (valor > 1000) return 0.08;
        return 0.01;
    }
}
