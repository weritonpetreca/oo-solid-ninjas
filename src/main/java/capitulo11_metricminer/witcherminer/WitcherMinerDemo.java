package capitulo11_metricminer.witcherminer;

import capitulo11_metricminer.witcherminer.core.OpcoesDoWitcherMiner;
import capitulo11_metricminer.witcherminer.estudos.EstudoDePerformanceDeCacadores;
import capitulo11_metricminer.witcherminer.estudos.EstudoDeMetricasDosContratos;

public class WitcherMinerDemo {
    public static void executar() {
        var opcoes = new OpcoesDoWitcherMiner("/guilda/historico", 4, "resultados/");
        
        System.out.println("==================================================");
        System.out.println("🐺 WITCHER MINER — FRAMEWORK COMPLETO");
        System.out.println("==================================================\n");

        System.out.println("  ─── Estudo 1: Performance dos Caçadores ───────────");
        new EstudoDePerformanceDeCacadores().executar(opcoes);
        System.out.println();

        System.out.println("  ─── Estudo 2: Métricas dos Contratos ──────────────");
        new EstudoDeMetricasDosContratos().executar(opcoes);
        System.out.println();

        System.out.println("  🎯 O PODER DA ARQUITETURA FINAL:");
        System.out.println("    Tudo separado. Classes públicas em pacotes fechados.");
        System.out.println("    O compilador não reclama, o DIP funciona, o mundo está salvo.");
    }
}
