package capitulo4_ocp.v6_calculadora_strategy_map.ports;

/**
 * 📜 A PORTA (INTERFACE)
 *
 * Define o contrato para qualquer tabela de preços.
 * O Use Case (Calculadora) depende desta interface, não da implementação.
 */
public interface TabelaDePreco {
    double descontoPara(double valor);
}
