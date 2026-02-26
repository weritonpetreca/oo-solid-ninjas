package capitulo4_ocp.v6_calculadora_strategy_map.adapters;

import capitulo4_ocp.v6_calculadora_strategy_map.ports.TabelaDePreco;
import org.springframework.stereotype.Component;

/**
 * 📜 A TABELA PADRÃO (ADAPTER)
 *
 * Implementação concreta de uma tabela de preços sem desconto.
 *
 * 🛡️ SPRING COMPONENT:
 * A anotação @Component("PADRAO") registra esta classe no Spring com o nome "PADRAO".
 * Isso permite que o Spring injete esta classe automaticamente no MAPA de estratégias.
 */
@Component("PADRAO")
public class TabelaSemDesconto implements TabelaDePreco {

    @Override
    public double descontoPara(double valor) {
        return 0;
    }
}
