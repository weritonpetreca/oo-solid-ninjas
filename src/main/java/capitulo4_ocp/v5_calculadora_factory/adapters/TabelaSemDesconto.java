package capitulo4_ocp.v5_calculadora_factory.adapters;

import capitulo4_ocp.v5_calculadora_factory.ports.TabelaDePreco;

/**
 * 📜 A TABELA PADRÃO (ADAPTER)
 *
 * Implementação concreta de uma tabela de preços sem desconto.
 */
public class TabelaSemDesconto implements TabelaDePreco {

    @Override
    public double descontoPara(double valor) {
        return 0;
    }
}
