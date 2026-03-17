package capitulo9_maus_cheiros.v2_feature_envy;

public class DemoV2 {

    public static void executar() {
        System.out.println("  ❌ ProcessadorDeContratosComSmell — Feature Envy:");
        var contratoSmell = new ContratoDeCacaComSmell("Manticora", 1000.0, 4);
        new ProcessadorDeContratosComSmell().processarContrato(contratoSmell);
        System.out.printf("    imposto=R$%.2f, encerrado=%b%n",
                contratoSmell.getImposto(), contratoSmell.isEncerrado());

        System.out.println("  ✅ ContratoDeCaca processa a si mesmo:");
        var contrato = new ContratoDeCaca("Manticora", 1000.0, 4);
        new ProcessadorDeContratosLimpo().processar(contrato);
        System.out.printf("    imposto=R$%.2f, encerrado=%b%n",
                contrato.getImposto(), contrato.isEncerrado());
    }
}
