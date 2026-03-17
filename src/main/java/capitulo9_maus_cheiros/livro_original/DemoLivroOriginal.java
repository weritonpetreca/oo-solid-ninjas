package capitulo9_maus_cheiros.livro_original;

public class DemoLivroOriginal {

    public static void executar() {
        demo91_RefusedBequest();
        demo92_FeatureEnvy();
        demo93_IntimidadeInapropriada();
        demo94_96_GodClassDivergentShotgun();
    }

    private static void demo91_RefusedBequest() {
        System.out.println("  9.1 — Refused Bequest");

        var nfSmell = new NotaFiscalComSmell(1000.0, 5);
        System.out.printf("    ❌ NotaFiscalComSmell (herda Matematica): imposto=R$%.2f%n", nfSmell.calculaImposto());

        var sistema = new SistemaComSmell();
        sistema.algumComportamento(nfSmell); // ❌ sistema recebe NF onde esperava Matematica

        var nfCorreta = new NotaFiscalCorreta(1000.0, 5);
        System.out.printf("    ✅ NotaFiscalCorreta (composição):        imposto=R$%.2f%n", nfCorreta.calculaImposto());
        System.out.println("    ✅ NotaFiscalCorreta NÃO é Matematica: " +
                !Matematica.class.isAssignableFrom(nfCorreta.getClass()));
    }

    private static void demo92_FeatureEnvy() {
        System.out.println("  9.2 — Feature Envy");

        var nfSmell = new NotaFiscal(1000.0, "Mauricio", 3);
        new Gerenciador(new Usuario("admin")).processa(nfSmell);
        System.out.printf("    ❌ Gerenciador (feature envy): imposto=R$%.2f, finalizada=%b%n",
                nfSmell.getValorImposto(), nfSmell.isFinalizada());

        var nfLimpa = new NotaFiscalComportamental(1000.0, "Mauricio", 3);
        nfLimpa.processar();
        System.out.printf("    ✅ NotaFiscalComportamental:   imposto=R$%.2f, finalizada=%b%n",
                nfLimpa.getValorImposto(), nfLimpa.isFinalizada());
    }

    private static void demo93_IntimidadeInapropriada() {
        System.out.println("  9.3 — Intimidade Inapropriada");

        var nfSmell = new NotaFiscalIntimidade(8000.0);
        nfSmell.encerrar();
        new ProcessadorComIntimidade().processa(nfSmell);
        System.out.println("    ❌ ProcessadorComIntimidade decide fora: importante=" + nfSmell.isImportante());

        var nfEncapsulada = new NotaFiscalEncapsulada(8000.0);
        new ProcessadorLimpo().processa(nfEncapsulada);
        System.out.println("    ✅ NotaFiscalEncapsulada decide sozinha: importante=" + nfEncapsulada.isImportante());
    }

    private static void demo94_96_GodClassDivergentShotgun() {
        System.out.println("  9.4/9.5/9.6 — God Class / Divergent Changes / Shotgun Surgery");

        var aliquota = new AliquotaDeImposto();
        System.out.printf("    ✅ AliquotaDeImposto centralizada: 15%% de R$2000 = R$%.2f%n",
                aliquota.calcular(2000.0));

        var shotgun = new EmissorDeNotaShotgun();
        System.out.printf("    ❌ EmissorShotgun (hardcoded):     15%% de R$2000 = R$%.2f%n",
                shotgun.calcularImposto(2000.0));

        var validador = new ValidadorDeNotaShotgun();
        System.out.println("    ✅ Consistência entre centralizado e shotgun: " +
                validador.impostoCorreto(2000.0, aliquota.calcular(2000.0)));
    }
}
