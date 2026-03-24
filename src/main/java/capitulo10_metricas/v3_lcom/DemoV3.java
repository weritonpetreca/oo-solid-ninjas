package capitulo10_metricas.v3_lcom;

public class DemoV3 {

    public static void executar() {
        var calc = new CalculadorLCOM();

        System.out.println("  ❌ GuildaComDuasResponsabilidades — LCOM alto:");
        System.out.println("    Grupo A (Financeiro): recompensa, taxa");
        System.out.println("      → calcularLiquida(), calcularImposto()");
        System.out.println("    Grupo B (Reputação): nome, totalMissoes, ranking");
        System.out.println("      → promover(), gerarInsignia(), rebaixar()");
        System.out.println("    Os grupos NÃO se cruzam — dois conjuntos independentes");
        // M=5 métodos, F=5 atributos, MF: recompensa(2)+taxa(2)+nome(2)+totalMissoes(2)+ranking(3) = 11
        double lcomRuim = calc.calcularLcomHS(5, 5, 11);
        System.out.printf("    LCOM HS ≈ %.2f → %s%n", lcomRuim, calc.avaliar(lcomRuim));
        System.out.println();

        System.out.println("  ✅ Após separação em duas classes:");
        System.out.println("    FinanceiroDeGuilda: M=3, F=2 (recompensa, taxa)");
        System.out.println("      → todos métodos acessam recompensa e/ou taxa");
        // M=3, F=2, MF: recompensa(3)+taxa(3) = 6
        double lcomFin = calc.calcularLcomHS(3, 2, 6);
        System.out.printf("    LCOM HS = %.2f → %s%n", lcomFin, calc.avaliar(lcomFin));

        System.out.println("    ReputacaoDeCacador: M=3, F=3 (nome, totalMissoes, ranking)");
        // M=3, F=3, MF: nome(2)+totalMissoes(2)+ranking(3) = 7
        double lcomRep = calc.calcularLcomHS(3, 3, 7);
        System.out.printf("    LCOM HS ≈ %.2f → %s%n", lcomRep, calc.avaliar(lcomRep));
        System.out.println();

        System.out.println("  Demo das classes coesas:");
        var fin = new FinanceiroDeGuilda(3000.0, 0.15);
        System.out.printf("    FinanceiroDeGuilda(3000, 15%%): líquida=R$%.2f, imposto=R$%.2f, premium=%b%n",
                fin.calcularLiquida(), fin.calcularImposto(), fin.isPremium());

        var rep = new ReputacaoDeCacador("Geralt de Rívia");
        rep.registrarMissao();
        rep.registrarMissao();
        rep.registrarMissao();
        rep.registrarMissao();
        rep.registrarMissao(); // 5 missões → EXPERIENTE
        System.out.println("    " + rep.gerarInsignia() + " | " + rep.getTotalMissoes() + " missões");

        System.out.println();
        System.out.println("  Aniche sobre LCOM (p.131):");
        System.out.println("    'Pense nessas métricas como um filtro. Você não consegue olhar");
        System.out.println("     os 50 mil métodos, mas consegue olhar um a um os 100 que ela filtrar.'");
    }
}