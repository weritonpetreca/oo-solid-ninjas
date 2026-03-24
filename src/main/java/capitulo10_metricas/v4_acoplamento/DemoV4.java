package capitulo10_metricas.v4_acoplamento;

public class DemoV4 {

    public static void executar() {
        System.out.println("  Classes de domínio estáveis (CA alto, CE baixo):");
        var r1 = new RelatorioAcoplamento("ContratoDeCaca",  15, 0);
        var r2 = new RelatorioAcoplamento("TaxaDaGuilda",    12, 0);
        System.out.println("    ContratoDeCaca  — CA=" + r1.ca() + " | " + r1.avaliarCA());
        System.out.println("                      CE=" + r1.ce() + " | " + r1.avaliarCE());
        System.out.println("    TaxaDaGuilda    — CA=" + r2.ca() + " | " + r2.avaliarCA());
        System.out.println("                      CE=" + r2.ce() + " | " + r2.avaliarCE());
        System.out.println();

        System.out.println("  ❌ GerenciadorCentralComAltoCE — CE = 5 (dependências instáveis):");
        var r3 = new RelatorioAcoplamento("GerenciadorCentral(antes)", 2, 5);
        System.out.println("    CE=" + r3.ce() + " depende de: Postgres, SMTP, Correios, ERP, Log (classes concretas)");
        System.out.println("    " + r3.avaliarCE());
        System.out.println("    → Trocar Postgres por Mongo obriga abrir esta classe");
        System.out.println();

        System.out.println("  ✅ GerenciadorLimpo — CE = 5 (dependências estáveis/interfaces):");
        var r4 = new RelatorioAcoplamento("GerenciadorLimpo(depois)", 2, 5);
        System.out.println("    CE=" + r4.ce() + " depende de: Repositorio, Notificador, Despachador, Integracao, Log");
        System.out.println("    → Mesma quantidade, mas INTERFACES ESTÁVEIS");
        System.out.println("    → Trocar Postgres por Mongo? Cria novo adapter — GerenciadorLimpo não muda");
        System.out.println();

        System.out.println("  Aniche, p.133:");
        System.out.println("    'Encontrar uma classe cujo acoplamento aferente é alto, assim como sua CC,");
        System.out.println("     nos dá indício de uma classe muito reutilizada, mas complicada.'");
        System.out.println("    → CA alto + CC alto = refatorar com cuidado (muita gente depende dela)");
        System.out.println("    → CA baixo + CE alto = candidata a remoção ou simplificação");
    }
}