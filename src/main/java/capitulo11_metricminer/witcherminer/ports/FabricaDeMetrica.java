package capitulo11_metricminer.witcherminer.ports;

public interface FabricaDeMetrica {
    MetricaDeCacador construir();
    String getNome();
}
