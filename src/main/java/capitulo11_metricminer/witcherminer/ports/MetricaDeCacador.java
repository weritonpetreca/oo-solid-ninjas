package capitulo11_metricminer.witcherminer.ports;

import capitulo11_metricminer.witcherminer.domain.AcaoDoCacador;
import java.util.List;

public interface MetricaDeCacador {
    double calcular(List<AcaoDoCacador> acoes);
}
