package capitulo4_ocp.v6_calculadora_strategy_map.adapters;

import capitulo4_ocp.v6_calculadora_strategy_map.ports.ServicoDeEntrega;
import org.springframework.stereotype.Component;

/**
 * 🚚 O MENSAGEIRO (ADAPTER)
 *
 * Implementação concreta de um serviço de entrega.
 *
 * 🛡️ SPRING COMPONENT:
 * A anotação @Component("CORREIOS") registra esta classe no Spring com o nome "CORREIOS".
 * Isso permite que o Spring injete esta classe automaticamente no MAPA de estratégias.
 */
@Component("CORREIOS")
public class FreteCorreios implements ServicoDeEntrega {

    @Override
    public double para(String cidade) {
        return "SÃO PAULO".equalsIgnoreCase(cidade) ? 15.0 : 30.0;
    }
}
