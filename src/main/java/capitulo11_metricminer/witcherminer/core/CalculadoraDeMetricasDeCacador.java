package capitulo11_metricminer.witcherminer.core;

import capitulo11_metricminer.witcherminer.domain.ContratoRegistrado;
import capitulo11_metricminer.witcherminer.domain.RegistroHistoricoGuilda;
import capitulo11_metricminer.witcherminer.ports.FabricaDeMetrica;
import capitulo11_metricminer.witcherminer.ports.MecanismoDePersistencia;
import capitulo11_metricminer.witcherminer.ports.MetricaDeCacador;
import capitulo11_metricminer.witcherminer.ports.VisitanteDeContrato;

public class CalculadoraDeMetricasDeCacador implements VisitanteDeContrato {
    private final FabricaDeMetrica fabrica;

    public CalculadoraDeMetricasDeCacador(FabricaDeMetrica fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public void processar(RegistroHistoricoGuilda registro, ContratoRegistrado contrato, MecanismoDePersistencia saida) {
        MetricaDeCacador metrica = fabrica.construir();
        double valor = metrica.calcular(contrato.getAcoes());

        saida.escrever(
                registro.getNome(), 
                contrato.getId(), 
                contrato.getCacador(), 
                fabrica.getNome(), 
                String.format("%.4f", valor)
        );
    }

    @Override
    public String nome() { 
        return "CalculadoraDeMetricas[" + fabrica.getNome() + "]"; 
    }
}
