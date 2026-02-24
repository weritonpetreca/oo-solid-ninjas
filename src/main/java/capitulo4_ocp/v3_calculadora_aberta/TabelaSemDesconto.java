package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 💰 TABELA SEM DESCONTO
 *
 * Às vezes a regra é simples: sem desconto.
 * Mas ainda precisamos de um objeto que implemente o contrato.
 *
 * ⚔️ NULL OBJECT PATTERN:
 * Em vez de passar `null` e lidar com NullPointerException,
 * criamos um objeto que representa "sem desconto".
 * Mais seguro, mais expressivo.
 */

public class TabelaSemDesconto implements TabelaDePreco {
    @Override
    public double descontoPara(double valor) {
        return 0;
    }
}
