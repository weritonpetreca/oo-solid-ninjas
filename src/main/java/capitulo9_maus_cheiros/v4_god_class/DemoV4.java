package capitulo9_maus_cheiros.v4_god_class;

public class DemoV4 {

    public static void executar() {
        System.out.println("  ❌ GuildaDoContienenteGodClass — faz absolutamente tudo:");
        var godClass = new GuildaDoContienenteGodClass();
        godClass.registrarCacador("Geralt");
        godClass.criarContrato("Grifo", 2000.0);
        godClass.concluirMissao("Geralt", "Grifo", 2000.0);
        System.out.println("    " + godClass.gerarRelatorioMensal());

        System.out.println("  ✅ GuildaCoordenadora — responsabilidades distribuídas:");
        var registro    = new RegistroDeCacadores();
        var contratos   = new GestorDeContratos();
        var calc        = new CalculadorDeImpostoGuilda();
        var tesouro     = new TesouroDaGuilda(50_000.0);
        var notificacao = new ServicoDeNotificacao();
        var guilda      = new GuildaCoordenadora(registro, contratos, calc, tesouro, notificacao);

        guilda.concluirMissao("Geralt", "Grifo", 2000.0);
        System.out.printf("    Saldo do tesouro: R$%.2f%n", tesouro.getSaldo());
    }
}
