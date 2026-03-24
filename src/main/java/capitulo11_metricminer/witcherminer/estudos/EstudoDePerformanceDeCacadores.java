package capitulo11_metricminer.witcherminer.estudos;

import capitulo11_metricminer.witcherminer.adapters.*;
import capitulo11_metricminer.witcherminer.core.*;
import capitulo11_metricminer.witcherminer.ports.*;
import capitulo11_metricminer.witcherminer.visitantes.*;

public class EstudoDePerformanceDeCacadores implements EstudoDaGuilda {
    @Override
    public void executar(OpcoesDoWitcherMiner opcoes) {
        System.out.println("    [Estudo] EstudoDePerformanceDeCacadores iniciado");
        var registroGuilda = RegistroHistoricoFactory.criarGuilda("Guilda do Lobo");
        
        new NavegadorDeHistoricoGuilda(opcoes)
                .em(registroGuilda)
                .processar(new ContadorDeMissoesCompletas(), new CSVGuilda("missoes-completas.csv"))
                .processar(new CalculadoraDeRecompensas(), new CSVGuilda("recompensas.csv"))
                .processar(new AnalisadorDeCancelamentos(1), new CSVGuilda("alertas-cancelamentos.csv"))
                .iniciar();
    }
}
