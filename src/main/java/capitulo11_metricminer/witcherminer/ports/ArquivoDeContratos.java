package capitulo11_metricminer.witcherminer.ports;

import capitulo11_metricminer.witcherminer.domain.ContratoRegistrado;
import capitulo11_metricminer.witcherminer.domain.ReferenciaDeContrato;
import java.util.List;

public interface ArquivoDeContratos {
    List<ReferenciaDeContrato> getReferencias();
    ContratoRegistrado getContrato(String id);
}
