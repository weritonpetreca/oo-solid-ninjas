package capitulo11_metricminer.witcherminer.metricas;

import capitulo11_metricminer.witcherminer.ports.*;

public class FabricaTaxaDeSucesso implements FabricaDeMetrica {
    @Override public MetricaDeCacador construir() { return new TaxaDeSucessoMetrica(); }
    @Override public String getNome() { return "TAXA_SUCESSO"; }
}
