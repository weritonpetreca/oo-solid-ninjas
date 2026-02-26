package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 💰 TABELA DE PREÇO PADRÃO
 *
 * A implementação original do sistema.
 * Agora é apenas UMA das possíveis implementações de {@link TabelaDePreco}.
 *
 * 🛡️ SRP + OCP:
 * A única razão para mudar esta classe é se a REGRA PADRÃO de desconto mudar.
 * Nada mais.
 *
 * Regras:
 * - Acima de R$ 5.000: 3% de desconto
 * - Acima de R$ 1.000: 5% de desconto
 * - Demais: sem desconto
 */

public class TabelaPrecoPadrao implements TabelaDePreco {
    @Override
    public double descontoPara(double valor) {
        if (valor > 5000) return 0.03;
        if (valor > 1000) return 0.05;
        return 0;
    }
}

