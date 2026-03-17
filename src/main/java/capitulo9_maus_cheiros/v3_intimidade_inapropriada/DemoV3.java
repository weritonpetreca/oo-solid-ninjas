package capitulo9_maus_cheiros.v3_intimidade_inapropriada;

public class DemoV3 {

    public static void executar() {
        System.out.println("  ❌ GerenciadorDeMissoesComSmell — Intimidade Inapropriada:");
        var missaoSmell = new MissaoComSmell(9000.0, "EXTREMO");
        new GerenciadorDeMissoesComSmell().avaliarMissao(missaoSmell);
        System.out.printf("    prioridade calculada fora: R$%.2f%n",
                new GerenciadorDeMissoesComSmell().calcularPrioridade(missaoSmell));

        System.out.println("  ✅ Missao encapsula suas próprias regras (Tell, Don't Ask):");
        var missao = new Missao(9000.0, "EXTREMO");
        missao.aceitar();
        var gerenciador = new GerenciadorDeMissoesLimpo();
        gerenciador.encerrarMissao(missao);
        gerenciador.exibirPrioridade(missao);
        System.out.println("    urgente=" + missao.isUrgente() + ", encerrada=" + missao.isEncerrada());
    }
}
