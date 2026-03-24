package capitulo11_metricminer.witcherminer.metricas;

import capitulo11_metricminer.witcherminer.domain.AcaoDoCacador;
import capitulo11_metricminer.witcherminer.domain.TipoDeAcao;
import capitulo11_metricminer.witcherminer.ports.MetricaDeCacador;
import java.util.List;

public class IndiceDeCancelamentoMetrica implements MetricaDeCacador {
    @Override
    public double calcular(List<AcaoDoCacador> acoes) {
        long canceladas = acoes.stream()
                .filter(a -> a.getTipo() == TipoDeAcao.MISSAO_CANCELADA).count();
        if (acoes.isEmpty()) return 0.0;
        return (double) canceladas / acoes.size();
    }
}
