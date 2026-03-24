package capitulo11_metricminer.witcherminer.estudos;

import capitulo11_metricminer.witcherminer.adapters.*;
import capitulo11_metricminer.witcherminer.core.*;
import capitulo11_metricminer.witcherminer.ports.*;
import capitulo11_metricminer.witcherminer.metricas.*;

public class EstudoDeMetricasDosContratos implements EstudoDaGuilda {
    @Override
    public void executar(OpcoesDoWitcherMiner opcoes) {
        System.out.println("    [Estudo] EstudoDeMetricasDosContratos iniciado");
        var registroGuilda = RegistroHistoricoFactory.criarGuilda("Guilda do Lobo");
        
        new NavegadorDeHistoricoGuilda(opcoes)
                .em(registroGuilda)
                .processar(new CalculadoraDeMetricasDeCacador(new FabricaTaxaDeSucesso()), new CSVGuilda("taxa-sucesso.csv"))
                .iniciar();
    }
}
