package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 💰 TABELA DE PREÇO VIP
 *
 * Regra exclusiva para os melhores clientes do Continente.
 * Criada sem modificar uma linha da Calculadora.
 *
 * Regras:
 * - Acima de R$ 5.000: 12% de desconto
 * - Acima de R$ 1.000: 10% de desconto
 * - Demais: 5%
 */

public class TabelaDePrecoVip implements TabelaDePreco {
    @Override
    public double descontoPara(double valor) {
        if (valor > 5000) return 0.12;
        if (valor > 1000) return 0.10;
        return 0.05;
    }
}
