package capitulo12_conclusao;

/**
 * ⚔️ CAPÍTULO 12 — Conclusão
 * A Jornada Completa do Bruxo Programador
 */
public class SimuladorDaConclusao {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("=".repeat(62));
        System.out.println("  CAP 12 — CONCLUSÃO");
        System.out.println("  A Jornada Completa do Bruxo Programador");
        System.out.println("=".repeat(62));
        System.out.println();

        demonstrarJornadaCompleta();
        imprimirMapaDaPrincipios();
        imprimirLeiturасRecomendadas();
    }

    static void demonstrarJornadaCompleta() {
        System.out.println("[ SÍNTESE ] Todos os princípios em uma única classe");
        System.out.println();

        var registro = new RegistroDeBruxos();

        // Cap. 8 — Tiny Types + Construtor Rico + Null Object
        var geralt = new BruxoDoContinente(
                new NomeDeBruxo("Geralt de Rívia"),
                new EscolaDeBruxo("Escola do Lobo"),
                new TaxaVeterano(),                                         // Cap. 4 — Strategy
                new MensageiroReal("geralt@kaermorhen.com"),                // Cap. 8 — Null Object
                new LocalizacaoAtual("Velen", "Vizima")                    // Cap. 8 — Imutável
        );

        var lambert = new BruxoDoContinente(
                new NomeDeBruxo("Lambert"),
                new EscolaDeBruxo("Escola do Lobo"),
                new TaxaPadrao(),
                new MensageiroDesconhecido(),                               // Null Object
                new LocalizacaoAtual("Kaer Morhen", "Fortaleza")
        );

        var ciri = new BruxoDoContinente(
                new NomeDeBruxo("Ciri"),
                new EscolaDeBruxo("Escola do Lobo"),
                new TaxaMestre(),                                           // Cap. 4 — OCP
                new MensageiroReal("ciri@toussaint.com"),
                new LocalizacaoAtual("Toussaint", "Beauclair")
        );

        registro.registrar(geralt);
        registro.registrar(lambert);
        registro.registrar(ciri);

        // Cap. 5 — Tell, Don't Ask
        geralt.registrarMissaoConcluida("Grifo Real",    new RecompensaBruta(3000.0));
        geralt.registrarMissaoConcluida("Basilisco",     new RecompensaBruta(5000.0));
        geralt.registrarMissaoConcluida("Manticora",     new RecompensaBruta(4500.0));
        geralt.moverPara("Skellige", "Kaer Trolde");                      // Cap. 8 — Imutabilidade

        ciri.registrarMissaoConcluida("Djinn",           new RecompensaBruta(8000.0));
        ciri.registrarMissaoConcluida("Caçada Selvagem", new RecompensaBruta(15000.0));

        // Cap. 7 — ISP: Tributavel
        double impostoGeralt = geralt.calcularImposto();
        double impostoCiri   = ciri.calcularImposto();

        System.out.println("  Caçadores registrados:");
        for (var b : registro.listarDisponiveis()) {
            System.out.printf("    %-20s | %-15s | Missões: %-2d | Acumulado: R$%8.2f | Contato: %s%n",
                    b.getNome().valor(),
                    b.getEscola().nome(),
                    b.getMissoesConcluidass().size(),
                    b.getRecompensaAcumulada(),
                    b.temContato() ? "✅" : "❌");
        }
        System.out.println();

        System.out.println("  Cálculo de impostos (interface Tributavel — Cap. 7):");
        System.out.printf("    Geralt (taxa veterano 10%%): R$%.2f%n", impostoGeralt);
        System.out.printf("    Ciri   (taxa mestre   5%%):  R$%.2f%n", impostoCiri);
        System.out.println();

        System.out.println("  Localização após moverPara() (imutabilidade — Cap. 8):");
        System.out.println("    Geralt em: " + geralt.getLocalizacao().regiao()
                + " / " + geralt.getLocalizacao().cidade());
        System.out.println();

        System.out.println("  Notificações (Null Object — Cap. 8):");
        System.out.println("    " + geralt.notificar("Novo contrato disponível!"));
        System.out.println("    " + lambert.notificar("Reunião na Guilda."));
        System.out.println();

        // Cap. 8 — Optional em vez de null
        System.out.println("  Busca (Optional — Cap. 8):");
        registro.buscarPorNome("Ciri").ifPresentOrElse(
                b  -> System.out.println("    ✅ Encontrada: " + b.getNome().valor()),
                ()  -> System.out.println("    ❌ Não encontrada")
        );
        registro.buscarPorNome("Vesemir").ifPresentOrElse(
                b  -> System.out.println("    ✅ Encontrado: " + b.getNome().valor()),
                ()  -> System.out.println("    Vesemir não está registrado (Optional vazio, sem null)")
        );

        // Cap. 5 — lista imutável
        System.out.println();
        System.out.println("  Proteção da lista interna (Cap. 5):");
        try {
            geralt.getMissoesConcluidass().add("Tentativa de hack externo");
        } catch (UnsupportedOperationException e) {
            System.out.println("    ❌ Lista imutável — modificação bloqueada corretamente.");
        }
    }

    static void imprimirMapaDaPrincipios() {
        System.out.println();
        System.out.println("=".repeat(62));
        System.out.println("  MAPA DA JORNADA — Todos os Capítulos");
        System.out.println("=".repeat(62));
        System.out.println("  Cap. 1  OO Mindset    → objetos com estado e comportamento");
        System.out.println("  Cap. 2  SRP           → uma razão para mudar por classe");
        System.out.println("  Cap. 3  DIP           → dependa de abstrações, não concretos");
        System.out.println("  Cap. 4  OCP           → aberto para extensão, fechado para modificação");
        System.out.println("  Cap. 5  Encapsulamento → esconda o COMO, exponha o QUÊ");
        System.out.println("  Cap. 6  LSP / Composição → favoreça composição sobre herança");
        System.out.println("  Cap. 7  ISP           → interfaces magras e precisas");
        System.out.println("  Cap. 8  Consistência  → construtores ricos, tiny types, imutabilidade");
        System.out.println("  Cap. 9  Maus Cheiros  → reconheça e nomeie os problemas");
        System.out.println("  Cap. 10 Métricas      → CC, LCOM, CA, CE como filtros");
        System.out.println("  Cap. 11 MetricMiner   → SOLID em um framework real");
        System.out.println("  Cap. 12 Conclusão     → pratique, valide, melhore sempre");
        System.out.println("=".repeat(62));
    }

    static void imprimirLeiturасRecomendadas() {
        System.out.println();
        System.out.println("  Aniche, p.147-148 — Leituras recomendadas:");
        System.out.println();
        System.out.println("  📚 Agile Principles, Patterns and Practices");
        System.out.println("     Robert Martin — SOLID com profundidade extrema");
        System.out.println();
        System.out.println("  📚 Growing Object-Oriented Software, Guided by Tests");
        System.out.println("     Steve Freeman & Nat Pryce — TDD e design de qualidade");
        System.out.println();
        System.out.println("  📚 Design Patterns (Gang of Four)");
        System.out.println("     Catálogo de OO bem aplicada — padrões que transformam");
        System.out.println();
        System.out.println("  📚 Refactoring to Patterns");
        System.out.println("     Joshua Kerievsky — de código procedural para OO real");
        System.out.println();
        System.out.println("  \"Fazer tudo isso requer prática e experiência.");
        System.out.println("   Você não criará um bom projeto de classes de primeira;");
        System.out.println("   valide, aprenda e melhore o seu projeto o tempo todo.\"");
        System.out.println("                                        — Maurício Aniche");
        System.out.println();
        System.out.println("  \"Vá, programe, e que seu código seja limpo como a lâmina de Geralt.\"");
        System.out.println("=".repeat(62));
    }
}