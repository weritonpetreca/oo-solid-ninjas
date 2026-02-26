package capitulo4_ocp.v5_calculadora_factory.adapters;

import capitulo4_ocp.v5_calculadora_factory.ports.TabelaDePreco;

/**
 * 👑 A TABELA REAL (ADAPTER)
 *
 * Implementação concreta de uma tabela de preços.
 */
public class TabelaDePrecoVip implements TabelaDePreco {

    @Override
    public double descontoPara(double valor) {
        return 0.15;
    }
}
