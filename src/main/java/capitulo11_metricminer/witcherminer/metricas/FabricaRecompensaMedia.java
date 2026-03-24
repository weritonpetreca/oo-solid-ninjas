package capitulo11_metricminer.witcherminer.metricas;

import capitulo11_metricminer.witcherminer.ports.FabricaDeMetrica;
import capitulo11_metricminer.witcherminer.ports.MetricaDeCacador;

public class FabricaRecompensaMedia implements FabricaDeMetrica {
    @Override
    public MetricaDeCacador construir() {
        return new RecompensaMediaMetrica();
    }

    @Override
    public String getNome() {
        return "RECOMPENSA_MEDIA";
    }
}
