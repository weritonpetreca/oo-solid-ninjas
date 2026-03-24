package capitulo11_metricminer.livro_original;

import java.util.List;

// ─── ClassLevelMetricCalculator (p.143) ──────────────────────────────────────

/**
 * Aniche, p.143:
 * "um visitante de commit que já vem com o framework é o
 *  ClassLevelMetricCalculator. Ele é bastante simples: ele executa uma
 *  métrica de código para cada modificação existente."
 *
 * "A calculadora de métricas recebe a métrica que será executada em seu
 *  construtor. Mas não a métrica diretamente, e sim a fábrica."
 *
 * ".process(new ClassLevelMetricCalculator(new LackOfCohesionFactory()),
 *            new CSVFile("lcom.csv"))"
 */
class ClassLevelMetricCalculator implements CommitVisitor {

    private final ClassLevelMetricFactory fabrica;

    ClassLevelMetricCalculator(ClassLevelMetricFactory fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        for (Modification m : commit.getModifications()) {
            if (m.getType() == ModificationType.ADD || m.getType() == ModificationType.MODIFY) {
                if (m.getSourceCode() != null && !m.getSourceCode().isBlank()) {
                    // Nova instância a cada classe — Aniche, p.243: estados não devem vazar
                    ClassLevelMetric metrica = fabrica.build();
                    double valor = metrica.calculate(m.getSourceCode());
                    writer.write(
                            repo.getLastDir(),
                            commit.getHash(),
                            m.getNewPath(),
                            fabrica.getName(),
                            String.format("%.2f", valor));
                }
            }
        }
    }

    @Override
    public String name() { return "ClassLevelMetricCalculator[" + fabrica.getName() + "]"; }
}

// ─── Implementações de métricas ───────────────────────────────────────────────

/**
 * Aniche, p.143 — referenciado como "LackOfCohesionFactory".
 * Conta campos e métodos como heurística simplificada de LCOM.
 */
class LackOfCohesionFactory implements ClassLevelMetricFactory {
    @Override
    public ClassLevelMetric build() { return new LackOfCohesionMetric(); }
    @Override
    public String getName()        { return "LCOM"; }
}

class LackOfCohesionMetric implements ClassLevelMetric {
    @Override
    public double calculate(String sourceCode) {
        // Heurística simplificada: conta atributos e métodos declarados
        long atributos = sourceCode.lines()
                .filter(l -> l.contains("private ") && !l.contains("(") && !l.contains("class"))
                .count();
        long metodos = sourceCode.lines()
                .filter(l -> l.contains("public ") && l.contains("(") && l.contains("{"))
                .count();
        if (metodos == 0 || atributos == 0) return 0;
        return 1.0 - ((double) metodos / (atributos + metodos));
    }
}

/** Complexidade Ciclomática simplificada */
class ComplexidadeCiclomaticaFactory implements ClassLevelMetricFactory {
    @Override
    public ClassLevelMetric build() { return new ComplexidadeCiclomaticaMetric(); }
    @Override
    public String getName()        { return "CC"; }
}

class ComplexidadeCiclomaticaMetric implements ClassLevelMetric {
    @Override
    public double calculate(String sourceCode) {
        long desvios = sourceCode.lines()
                .filter(l -> l.contains(" if ") || l.contains(" for ") ||
                        l.contains(" while ") || l.contains(" switch ") ||
                        l.contains(" catch "))
                .count();
        return desvios + 1;
    }
}

// ─── Demo fachada ─────────────────────────────────────────────────────────────

public class DemoLivroOriginal {

    public static void executar() {
        System.out.println("  Estrutura do MetricMiner (Aniche, Cap. 11):");
        System.out.println("    Study → executa o estudo");
        System.out.println("    SourceCodeRepositoryNavigator → navega nos commits");
        System.out.println("    SCMRepository → representa o projeto");
        System.out.println("    SCM (interface) → acessa o repositório (Git, SVN...)");
        System.out.println("    CommitVisitor   → executa ação em cada commit");
        System.out.println("    PersistenceMechanism → salva resultados");
        System.out.println("    ClassLevelMetricFactory → cria métricas (nova instância por classe)");
        System.out.println();

        System.out.println("  Executando ContadorDeClassesRemovidas:");
        var opts    = new MMOptions("/projetos", 2, "resultados.csv");
        var repo    = SCMRepositoryFactory.criarFake("MetricMiner");
        var csv     = new CSVFile("addsremoves.csv");
        var visitor = new ContadorDeClassesRemovidas();

        for (var cs : repo.getScm().getChangeSets()) {
            visitor.process(repo, repo.getScm().getCommit(cs.getId()), csv);
        }
        csv.close();

        System.out.println();
        System.out.println("  Executando ClassLevelMetricCalculator (LCOM):");
        var csvLcom   = new CSVFile("lcom.csv");
        var calcLcom  = new ClassLevelMetricCalculator(new LackOfCohesionFactory());
        for (var cs : repo.getScm().getChangeSets()) {
            calcLcom.process(repo, repo.getScm().getCommit(cs.getId()), csvLcom);
        }
        csvLcom.close();

        System.out.println();
        System.out.println("  Executando SeleniumStudy (fluent API):");
        new SeleniumStudy().execute(opts);
    }
}
