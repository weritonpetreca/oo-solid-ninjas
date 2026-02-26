package capitulo4_ocp.v6_calculadora_strategy_map.adapters;

import capitulo4_ocp.v6_calculadora_strategy_map.ports.ServicoDeEntrega;
import org.springframework.stereotype.Component;

/**
 * 🎁 O PRESENTE (ADAPTER)
 *
 * Outra implementação concreta.
 *
 * 🛡️ SPRING COMPONENT:
 * A anotação @Component("GRATIS") registra esta classe no Spring com o nome "GRATIS".
 * Isso permite que o Spring injete esta classe automaticamente no MAPA de estratégias.
 */
@Component("GRATIS")
public class FreteGratis implements ServicoDeEntrega {

    @Override
    public double para(String cidade) {
        return 0;
    }
}
