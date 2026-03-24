package capitulo11_metricminer.witcherminer.metricas;

import capitulo11_metricminer.witcherminer.domain.*;
import capitulo11_metricminer.witcherminer.ports.MetricaDeCacador;
import java.util.List;

public class TaxaDeSucessoMetrica implements MetricaDeCacador {
    @Override
    public double calcular(List<AcaoDoCacador> acoes) {
        long tentadas = acoes.stream().filter(a -> a.getTipo() == TipoDeAcao.MISSAO_NOVA).count();
        long concluidas = acoes.stream().filter(a -> a.getTipo() == TipoDeAcao.MISSAO_CONCLUIDA).count();
        if (tentadas == 0) return 0.0;
        return (double) concluidas / tentadas;
    }
}
