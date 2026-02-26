package capitulo4_ocp.v5_calculadora_factory.adapters;

import capitulo4_ocp.v5_calculadora_factory.ports.ServicoDeEntrega;

/**
 * 🎁 O PRESENTE (ADAPTER)
 *
 * Outra implementação concreta.
 * Mostra como podemos ter múltiplos comportamentos para a mesma interface.
 */
public class FreteGratis implements ServicoDeEntrega {

    @Override
    public double para(String cidade) {
        return 0;
    }
}
