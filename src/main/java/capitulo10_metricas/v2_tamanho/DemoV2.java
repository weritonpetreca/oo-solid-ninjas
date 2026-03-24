package capitulo10_metricas.v2_tamanho;

public class DemoV2 {

    public static void executar() {
        System.out.println("  ❌ RegistroDeGuildaGigante — métricas alarmantes:");
        var metricasRuim = new MetricasDeTamanho("RegistroDeGuildaGigante", 9, 12, 9);
        System.out.println("    " + metricasRuim.avaliar());
        System.out.println("    processarMissao(): CC=7, NOP=4, MLOC≈28");
        System.out.println();

        System.out.println("  ✅ Classes após refatoração:");
        var m1 = new MetricasDeTamanho("IdentidadeDeCacador",  3, 2, 3);
        var m2 = new MetricasDeTamanho("CarteiraDeRecompensas", 2, 3, 1);
        var m3 = new MetricasDeTamanho("ProcessadorDeMissao",   0, 2, 2);
        System.out.println("    IdentidadeDeCacador:   " + m1.avaliar());
        System.out.println("    CarteiraDeRecompensas: " + m2.avaliar());
        System.out.println("    ProcessadorDeMissao:   " + m3.avaliar());
        System.out.println();

        System.out.println("  Demonstrando as classes refatoradas:");
        var identidade = new IdentidadeDeCacador("Geralt de Rívia", "Escola do Lobo", "G01-LOBO");
        var carteira   = new CarteiraDeRecompensas();
        var processor  = new ProcessadorDeMissao();

        double bruta  = 3000.0;
        double liquida = processor.calcularLiquida(bruta, 0.15);
        carteira.registrar(liquida);

        System.out.println("    Caçador: " + identidade);
        System.out.printf ("    Missão: bruta=R$%.2f → líquida=R$%.2f%n", bruta, liquida);
        System.out.println("    Classificação: " + processor.classificar(liquida, false));
        System.out.printf ("    Acumulado: R$%.2f | Total missões: %d%n",
                carteira.getAcumulado(), carteira.getMissoes());

        System.out.println();
        System.out.println("  Regra geral (Aniche, Cap. 10):");
        System.out.println("    NOA > 7  → possível God Class ou Divergent Changes");
        System.out.println("    NOM > 20 → responsabilidades demais");
        System.out.println("    NOP > 5  → use Tiny Types (Cap. 8) ou Builder");
        System.out.println("    MLOC > 15 → candidato a Extract Method");
    }
}