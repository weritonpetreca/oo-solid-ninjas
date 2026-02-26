package capitulo4_ocp.v5_calculadora_factory.ports;

/**
 * 📜 A PORTA (INTERFACE)
 *
 * Define o contrato para qualquer tabela de preços.
 * O Use Case (Calculadora) depende desta interface, não da implementação.
 */
public interface TabelaDePreco {
    double descontoPara(double valor);
}
