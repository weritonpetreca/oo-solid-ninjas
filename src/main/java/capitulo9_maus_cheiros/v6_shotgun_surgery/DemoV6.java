package capitulo9_maus_cheiros.v6_shotgun_surgery;

public class DemoV6 {

    public static void executar() {
        System.out.println("  ❌ Shotgun Surgery — taxa hardcoded em múltiplos lugares:");
        System.out.printf("    ProcessadorShotgun:  R$%.2f%n",
                new ProcessadorDeContratoComShotgun().calcularRecompensaLiquida(2000.0));
        System.out.println("    " + new RelatorioDeContratoComShotgun().gerarLinha("Harpia", 2000.0));

        System.out.println("  ✅ TaxaDaGuilda centralizada — mudar a taxa = um único lugar:");
        var taxa = new TaxaDaGuilda();
        System.out.printf("    ProcessadorLimpo:    R$%.2f%n",
                new ProcessadorDeContratoLimpo(taxa).calcularRecompensaLiquida(2000.0));
        System.out.println("    " + new RelatorioDeContratoLimpo(taxa).gerarLinha("Harpia", 2000.0));
        System.out.println("    Auditoria consistente: " +
                new AuditoriaDeContratoLimpo(taxa).taxaEstaCorreta(2000.0, taxa.calcularLiquida(2000.0)));
    }
}
