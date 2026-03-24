package capitulo10_metricas.v1_complexidade_ciclomatica;

import java.util.List;

public class DemoV1 {

    public static void executar() {
        System.out.println("  ❌ EstrategiasDeCacaComAltaCC — método definirEstrategia():");
        System.out.println("    CC = 12 (11 desvios de fluxo + 1)");
        System.out.println("    12 caminhos de execução → 12 casos de teste para cobertura total");
        System.out.println("    → Cada novo monstro adiciona mais IFs → God Method crescendo");
        System.out.println();

        System.out.println("  ✅ BruxoEstrategista com polimorfismo:");
        var bruxo = new BruxoEstrategista();
        var grifo     = new Grifo();
        var strige    = new Strige();
        var lobisomem = new Lobisomem();

        System.out.printf("    Grifo (CHUVA, ESPADA_PRATA):      %s%n",
                bruxo.decidir(grifo,     "CHUVA",    "ESPADA_PRATA"));
        System.out.printf("    Grifo (SOL, ESPADA_PRATA):        %s%n",
                bruxo.decidir(grifo,     "SOL",      "ESPADA_PRATA"));
        System.out.printf("    Strige (NOITE, ESPADA_PRATA):     %s%n",
                bruxo.decidir(strige,    "NOITE",    "ESPADA_PRATA"));
        System.out.printf("    Strige (NOITE, ESPADA_ACO):       %s%n",
                bruxo.decidir(strige,    "NOITE",    "ESPADA_ACO"));
        System.out.printf("    Lobisomem (LUA_CHEIA, NENHUMA):   %s%n",
                bruxo.decidir(lobisomem, "LUA_CHEIA","NENHUMA"));
        System.out.println("    → CC do método decidir() = 1 (zero desvios + 1)");
        System.out.println();

        var relatorio = new RelatorioDeCC();
        System.out.println("  Relatório de CC por método:");
        relatorio.imprimir(List.of(
                RelatorioDeCC.MetodoAnalisado.de("EstrategiasDeCacaComAltaCC.definirEstrategia()", 11),
                RelatorioDeCC.MetodoAnalisado.de("BruxoEstrategista.decidir()",                    0),
                RelatorioDeCC.MetodoAnalisado.de("Grifo.decidirEstrategia()",                       1),
                RelatorioDeCC.MetodoAnalisado.de("Strige.decidirEstrategia()",                      2),
                RelatorioDeCC.MetodoAnalisado.de("Lobisomem.decidirEstrategia()",                   2)
        ));
    }
}