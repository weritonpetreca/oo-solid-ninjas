package capitulo11_metricminer.witcherminer.metricas;

import capitulo11_metricminer.witcherminer.domain.AcaoDoCacador;
import capitulo11_metricminer.witcherminer.domain.TipoDeAcao;
import capitulo11_metricminer.witcherminer.ports.MetricaDeCacador;
import java.util.List;

public class RecompensaMediaMetrica implements MetricaDeCacador {
    @Override
    public double calcular(List<AcaoDoCacador> acoes) {
        double totalRecompensa = acoes.stream()
                .filter(a -> a.getTipo() == TipoDeAcao.RECOMPENSA_PAGA)
                .mapToDouble(AcaoDoCacador::getRecompensa)
                .sum();
        long concluidas = acoes.stream()
                .filter(a -> a.getTipo() == TipoDeAcao.MISSAO_CONCLUIDA).count();
        if (concluidas == 0) return 0.0;
        return totalRecompensa / concluidas;
    }
}
