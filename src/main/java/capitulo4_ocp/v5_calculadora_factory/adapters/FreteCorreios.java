package capitulo4_ocp.v5_calculadora_factory.adapters;

import capitulo4_ocp.v5_calculadora_factory.ports.ServicoDeEntrega;

/**
 * 🚚 O MENSAGEIRO (ADAPTER)
 *
 * Implementação concreta de um serviço de entrega.
 * Esta classe pode mudar (ex: API dos Correios mudou) sem afetar a Calculadora.
 */
public class FreteCorreios implements ServicoDeEntrega {

    @Override
    public double para(String cidade) {
        return "SÃO PAULO".equalsIgnoreCase(cidade) ? 15.0 : 30.0;
    }
}
