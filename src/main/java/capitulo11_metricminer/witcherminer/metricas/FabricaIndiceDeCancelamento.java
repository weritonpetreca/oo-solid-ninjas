package capitulo11_metricminer.witcherminer.metricas;

import capitulo11_metricminer.witcherminer.ports.FabricaDeMetrica;
import capitulo11_metricminer.witcherminer.ports.MetricaDeCacador;

public class FabricaIndiceDeCancelamento implements FabricaDeMetrica {
    @Override
    public MetricaDeCacador construir() {
        return new IndiceDeCancelamentoMetrica();
    }

    @Override
    public String getNome() {
        return "INDICE_CANCELAMENTO";
    }
}
