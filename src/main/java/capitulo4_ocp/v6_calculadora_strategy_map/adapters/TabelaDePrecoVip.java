package capitulo4_ocp.v6_calculadora_strategy_map.adapters;

import capitulo4_ocp.v6_calculadora_strategy_map.ports.TabelaDePreco;
import org.springframework.stereotype.Component;

/**
 * 👑 A TABELA REAL (ADAPTER)
 *
 * Implementação concreta de uma tabela de preços.
 *
 * 🛡️ SPRING COMPONENT:
 * A anotação @Component("VIP") registra esta classe no Spring com o nome "VIP".
 * Isso permite que o Spring injete esta classe automaticamente no MAPA de estratégias.
 */
@Component("VIP")
public class TabelaDePrecoVip implements TabelaDePreco {

    @Override
    public double descontoPara(double valor) {
        return 0.15;
    }
}
