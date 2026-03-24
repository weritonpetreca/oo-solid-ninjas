package capitulo11_metricminer.witcherminer.ports;

public interface MecanismoDePersistencia {
    void escrever(Object... linha);
    void fechar();
}
