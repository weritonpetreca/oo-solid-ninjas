package capitulo9_maus_cheiros.v5_divergent_changes;

public class DemoV5 {

    public static void executar() {
        System.out.println("  ❌ ProcessadorDeMissaoDivergente — múltiplas razões para mudar:");
        var divergente = new ProcessadorDeMissaoDivergente("Grifo Real", 3000.0, "Geralt");
        System.out.printf("    imposto=R$%.2f%n", divergente.calcularImposto());
        System.out.println("    classificação: " + divergente.classificarMonstro());
        System.out.println("    " + divergente.gerarRelatorio());

        System.out.println("  ✅ Classes coesas — uma razão para mudar cada:");
        var dados = new DadosDaMissao("Grifo Real", 3000.0, "Geralt");
        System.out.printf("    CalculadorDeImpostoMissao: R$%.2f%n",
                new CalculadorDeImpostoMissao().calcular(dados));
        System.out.println("    ClassificadorDeMonstro:   " +
                new ClassificadorDeMonstro().classificar(dados.nomeMonstro()));
        System.out.println("    GeradorDeRelatorioMissao: " +
                new GeradorDeRelatorioMissao().gerar(dados));
    }
}
