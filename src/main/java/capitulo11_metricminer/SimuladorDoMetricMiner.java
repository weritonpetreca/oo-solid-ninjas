package capitulo11_metricminer;

import capitulo11_metricminer.livro_original.DemoLivroOriginal;
import capitulo11_metricminer.witcherminer.WitcherMinerDemo;

public class SimuladorDoMetricMiner {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("==================================================");
        System.out.println("📊 CAPÍTULO 11: ESTUDO DE CASO — METRIC MINER");
        System.out.println("==================================================\n");

        System.out.println(">>> 1. RODANDO O CÓDIGO DO LIVRO ORIGINAL (Aniche)");
        DemoLivroOriginal.executar();
        
        System.out.println("\n\n>>> 2. RODANDO A ARQUITETURA FINAL (WITCHER MINER)");
        WitcherMinerDemo.executar();
    }
}
