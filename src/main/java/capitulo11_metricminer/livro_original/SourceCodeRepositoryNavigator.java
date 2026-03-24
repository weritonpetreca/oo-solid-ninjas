package capitulo11_metricminer.livro_original;

import java.util.ArrayList;
import java.util.List;

// ─── SourceCodeRepositoryNavigator (p.138–142) ───────────────────────────────

/**
 * Aniche, p.138:
 * "implementações de estudos fazem uso da classe
 *  SourceCodeRepositoryNavigator. Essa classe é a responsável por
 *  executar as ações desejadas pelo usuário em cada commit do projeto."
 *
 * Métodos do livro:
 *   in(SCMRepository... scm)
 *   process(CommitVisitor visitor, PersistenceMechanism writer)
 *   start()
 */
public class SourceCodeRepositoryNavigator {

    private final MMOptions opts;
    private final List<SCMRepository> repositorios = new ArrayList<>();
    private final List<ProcessConfig>  configs      = new ArrayList<>();

    public SourceCodeRepositoryNavigator(MMOptions opts) {
        this.opts = opts;
    }

    // ✅ Fluent API — Builder Pattern (Aniche, p.138/140)
    public SourceCodeRepositoryNavigator in(SCMRepository... scm) {
        for (SCMRepository r : scm) repositorios.add(r);
        return this;
    }

    public SourceCodeRepositoryNavigator process(CommitVisitor visitor,
                                                 PersistenceMechanism writer) {
        configs.add(new ProcessConfig(visitor, writer));
        return this;
    }

    /**
     * Aniche, p.142:
     * "O método start() divide a quantidade de commits em diferentes listas
     *  (uma para cada thread) e dispara uma thread para cada conjunto."
     *
     * Aqui simulamos de forma síncrona para fins didáticos.
     */
    public void start() {
        System.out.println("    [Navigator] Iniciando processamento...");
        System.out.println("    [Navigator] Threads configuradas: " + opts.getThreads());

        for (SCMRepository repo : repositorios) {
            System.out.println("    [Navigator] Processando repositório: " + repo.getLastDir());
            List<ChangeSet> changeSets = repo.getScm().getChangeSets();

            for (ChangeSet cs : changeSets) {
                Commit commit = repo.getScm().getCommit(cs.getId());
                for (ProcessConfig cfg : configs) {
                    cfg.visitor().process(repo, commit, cfg.writer());
                }
            }

            for (ProcessConfig cfg : configs) {
                cfg.writer().close();
            }
        }
        System.out.println("    [Navigator] Processamento concluído.");
    }

    private record ProcessConfig(CommitVisitor visitor, PersistenceMechanism writer) {}
}

// ─── SeleniumStudy (p.140) ───────────────────────────────────────────────────

/**
 * Aniche, p.140 — estudo exato do livro (adaptado para compilar sem imports externos).
 *
 * "public class SeleniumStudy implements Study {
 *      @Override
 *      public void execute(MMOptions opts) {
 *          new SourceCodeRepositoryNavigator(opts)
 *              .in(GitRepository.build("/path/do/projeto"))
 *              .process(new AddsAndRemoves(), new CSVFile("addsremoves.csv"))
 *              .process(new CommittedTogether(), new CSVFile("committed.csv"))
 *              .start();
 *      }
 * }"
 */
class SeleniumStudy implements Study {

    @Override
    public void execute(MMOptions opts) {
        // Simulando GitRepository com uma implementação fake
        SCMRepository repo = SCMRepositoryFactory.criarFake("selenium-project");

        new SourceCodeRepositoryNavigator(opts)
                .in(repo)
                .process(new ContadorDeClassesRemovidas(), new CSVFile("addsremoves.csv"))
                .process(new ContadorDeModificacoes(),    new CSVFile("committed.csv"))
                .start();
    }
}

// ─── SCMRepositoryFactory ─────────────────────────────────────────────────────

/**
 * Factory estático para criar repositórios (Aniche, p.139):
 * "Para instanciar esses repositórios, existem factory methods."
 *
 * GitRepository.build("/path/do/projeto") no livro.
 */
class SCMRepositoryFactory {

    private SCMRepositoryFactory() {
        // Construtor privado para esconder o construtor público implícito.
    }

    public static SCMRepository criarFake(String nome) {
        return new SCMRepository(
                "/projetos/" + nome,
                "abc0001",
                "abc0010",
                new FakeSCM(nome)
        );
    }
}

// ─── Implementação fake de SCM para demos ────────────────────────────────────

class FakeSCM implements SCM {

    FakeSCM(String projeto) { }

    @Override
    public List<ChangeSet> getChangeSets() {
        return List.of(
                new ChangeSet("abc0001"),
                new ChangeSet("abc0002"),
                new ChangeSet("abc0003")
        );
    }

    @Override
    public Commit getCommit(String id) {
        // Declaramos explicitamente como List<Modification> e garantimos que o default tenha a tipagem correta
        List<Modification> mods = switch (id) {
            case "abc0001" -> List.of(
                    new Modification("GeradorDeNotaFiscal.java", ModificationType.ADD, "public class GeradorDeNotaFiscal {}"),
                    new Modification("NotaFiscal.java",          ModificationType.ADD, "public class NotaFiscal {}")
            );
            case "abc0002" -> List.of(
                    new Modification("ProcessadorAntigo.java",   ModificationType.DELETE, ""),
                    new Modification("GeradorDeNotaFiscal.java", ModificationType.MODIFY, "public class GeradorDeNotaFiscal { void gera(){} }")
            );
            case "abc0003" -> List.of(
                    new Modification("ServicoDePagamento.java",  ModificationType.ADD,    "public class ServicoDePagamento {}"),
                    new Modification("DaoAntigo.java",           ModificationType.DELETE, "")
            );
            // ✅ A MÁGICA: List.<Modification>of() avisa ao compilador que é uma lista vazia de modificações!
            default -> List.<Modification>of();
        };
        return new Commit(id, new Committer("Geralt de Rívia"), "Refatoração " + id, mods);
    }
}

// ─── Visitante extra para a demonstração ─────────────────────────────────────

class ContadorDeModificacoes implements CommitVisitor {

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        long adds    = commit.getModifications().stream().filter(m -> m.getType() == ModificationType.ADD).count();
        long deletes = commit.getModifications().stream().filter(m -> m.getType() == ModificationType.DELETE).count();
        long modifs  = commit.getModifications().stream().filter(m -> m.getType() == ModificationType.MODIFY).count();

        writer.write(repo.getLastDir(), commit.getHash(), adds, modifs, deletes);
    }

    @Override
    public String name() { return "ContadorDeModificacoes"; }
}