package capitulo10_metricas.livro_original;

/**
 * Fachada pública para demonstração das classes originais do Cap. 10.
 * Chamada pelo SimuladorDeMetricas.
 */
public class DemoLivroOriginal {

    public static void executar() {

        // ─── 10.1 Complexidade Ciclomática ────────────────────────────────────
        System.out.println("  10.1 — Complexidade Ciclomática (McCabe)");

        ExemploComplexidadeCiclomatica exemplo = new ExemploComplexidadeCiclomatica();
        CalculadorDeCC calc = new CalculadorDeCC();

        // Pseudo-código do método conta() para demo didática
        String pseudoConta = "if(a>10) total+=a+b; if(b>20) total+=a*2+b;";
        int ccConta = calc.calcular(pseudoConta);
        System.out.println("    conta(int a, int b) — CC = " + ccConta + " (2 ifs + 1)");

        String pseudoCalc = "return valorBase * aliquota;";
        int ccCalc = calc.calcular(pseudoCalc);
        System.out.println("    calcularImposto() — CC = " + ccCalc + " (zero desvios + 1)");

        // CC em nível de classe = soma dos métodos
        int ccClasse = calc.calcularParaClasse(3, 5, 2);
        System.out.println("    NotaFiscal (soma de 3 métodos) — CC total = " + ccClasse);
        System.out.println();

        // ─── 10.2 Tamanho ─────────────────────────────────────────────────────
        System.out.println("  10.2 — Tamanho de Métodos e Classes");
        System.out.println("    ✅ ClasseCoesa: NOA=2, NOM=3 — saudável");
        System.out.println("    ❌ ClasseGigante: NOA=8, NOM com CC=7 — candidata a refatoração");
        System.out.println();

        // ─── 10.3 LCOM ────────────────────────────────────────────────────────
        System.out.println("  10.3 — Coesão e a LCOM");
        System.out.println("    ✅ NotaFiscalCoesa: métodos compartilham atributos → LCOM ≈ 0");
        System.out.println("    ❌ ClasseComDuasResponsabilidades:");
        System.out.println("       Grupo Fiscal: {valorBase, aliquota} ↔ {calcularImposto, calcularLiquido}");
        System.out.println("       Grupo Email:  {emailDestinatario, template} ↔ {gerarMensagem, enviarNotificacao}");
        System.out.println("       Os grupos NÃO se cruzam → LCOM ≈ 1 → DIVIDIR a classe!");
        System.out.println();

        // ─── 10.4 Acoplamento ─────────────────────────────────────────────────
        System.out.println("  10.4 — Acoplamento Aferente e Eferente");
        System.out.println("    GerenciadorDeNotaFiscal:");
        System.out.println("    ❌ CE (Eferente) = 3 → depende de BancoDeDados, Email, ServicoExterno");
        System.out.println("       Mais dependências = mais frágil");
        System.out.println("    ✅ NotaFiscalCoesa:");
        System.out.println("       CA (Aferente) = alto (todos dependem dela) → estável");
        System.out.println("       CE (Eferente) = 0 (não depende de ninguém) → independente");
        System.out.println();

        // ─── 10.5 Nomenclatura ────────────────────────────────────────────────
        System.out.println("  10.5 — Má Nomenclatura");
        System.out.println("    ❌ double c(double x, double y) — nomes sem significado");
        System.out.println("    ❌ calcularTaxaDaGuildaDoContineenteParaO... — longo demais");
        System.out.println("    ✅ double calcularTaxaDaGuilda(double recompensa) — preciso e legível");

        System.out.println();
        System.out.println("  10.6/10.7 — Como avaliar os números:");
        System.out.println("    Não existe 'número mágico' absoluto.");
        System.out.println("    Use estatísticas do próprio projeto (distribuição, quartis).");
        System.out.println("    Pense nas métricas como um FILTRO — não como um oráculo.");
        System.out.println("    Ferramentas: SonarQube, PMD, Checkstyle, SpotBugs");
    }
}