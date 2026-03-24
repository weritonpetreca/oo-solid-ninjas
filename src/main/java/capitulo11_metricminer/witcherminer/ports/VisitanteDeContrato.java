package capitulo11_metricminer.witcherminer.ports;

import capitulo11_metricminer.witcherminer.domain.ContratoRegistrado;
import capitulo11_metricminer.witcherminer.domain.RegistroHistoricoGuilda;

public interface VisitanteDeContrato {
    void processar(RegistroHistoricoGuilda registro, ContratoRegistrado contrato, MecanismoDePersistencia saida);
    String nome();
}
