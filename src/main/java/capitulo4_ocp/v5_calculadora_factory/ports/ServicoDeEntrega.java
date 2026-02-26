package capitulo4_ocp.v5_calculadora_factory.ports;

/**
 * 📜 A PORTA (INTERFACE)
 *
 * Define o contrato para qualquer serviço de entrega.
 * O Use Case (Calculadora) depende desta interface, não da implementação.
 */
public interface ServicoDeEntrega {
    double para(String cidade);
}
