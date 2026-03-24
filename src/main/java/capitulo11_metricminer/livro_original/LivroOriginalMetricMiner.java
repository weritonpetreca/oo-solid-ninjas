package capitulo11_metricminer.livro_original;

import java.util.Calendar;
import java.util.List;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 11: MetricMiner
// Interfaces e classes exatamente como aparecem no livro (p.138–144)
// ══════════════════════════════════════════════════════════════════════════════

// ─── Interface Study (p.138) ──────────────────────────────────────────────────

/**
 * Aniche, p.138:
 * "Todo estudo executado pelo MetricMiner deve implementar a interface Study.
 *  Ela é bastante simples, e contém apenas um método."
 */
interface Study {
    void execute(MMOptions opts);
}

// ─── MMOptions (parâmetros de linha de comando) ───────────────────────────────

/**
 * Parâmetros de configuração passados ao estudo.
 * Aniche menciona: diretórios dos projetos, quantidade de threads, arquivo de saída.
 */
class MMOptions {
    private final String diretorioProjeto;
    private final int threads;
    private final String arquivoDeSaida;

    public MMOptions(String diretorioProjeto, int threads, String arquivoDeSaida) {
        this.diretorioProjeto = diretorioProjeto;
        this.threads          = threads;
        this.arquivoDeSaida   = arquivoDeSaida;
    }

    public String getDiretorioProjeto() { return diretorioProjeto; }
    public int    getThreads()          { return threads; }
    public String getArquivoDeSaida()   { return arquivoDeSaida; }
}

// ─── SCMRepository (p.139) ───────────────────────────────────────────────────

/**
 * Aniche, p.139:
 * "A classe SCMRepository é basicamente uma classe de domínio, com informações
 *  como o path físico, o primeiro commit e o último commit."
 */
class SCMRepository {
    private final String path;
    private final String primeiroCommit;
    private final String ultimoCommit;
    private final SCM    scm;

    SCMRepository(String path, String primeiroCommit, String ultimoCommit, SCM scm) {
        this.path           = path;
        this.primeiroCommit = primeiroCommit;
        this.ultimoCommit   = ultimoCommit;
        this.scm            = scm;
    }

    public String getPath()           { return path; }
    public String getLastDir()        { return path.substring(path.lastIndexOf('/') + 1); }
    public String getPrimeiroCommit() { return primeiroCommit; }
    public String getUltimoCommit()   { return ultimoCommit; }
    public SCM    getScm()            { return scm; }
}

// ─── Interface SCM (p.141) ───────────────────────────────────────────────────

/**
 * Aniche, p.141:
 * "se um desenvolvedor quiser dar suporte a um novo gerenciador de código,
 *  ele precisa apenas devolver a lista de hash dos commits [...] e os
 *  detalhes de commit em particular."
 */
interface SCM {
    List<ChangeSet> getChangeSets();
    Commit getCommit(String id);
}

// ─── ChangeSet (p.141) ───────────────────────────────────────────────────────

class ChangeSet {
    private final String id;

    ChangeSet(String id) { this.id = id; }
    public String getId() { return id; }
}

// ─── Commit (p.142) ──────────────────────────────────────────────────────────

/**
 * Aniche, p.142 — código exato do livro.
 */
class Commit {
    private String           hash;
    private Committer        committer;
    private String           msg;
    private List<Modification> modifications;
    private String           parent;
    private Calendar         date;

    Commit(String hash, Committer committer, String msg,
           List<Modification> modifications) {
        this.hash          = hash;
        this.committer     = committer;
        this.msg           = msg;
        this.modifications = modifications;
        this.date          = Calendar.getInstance();
    }

    public String              getHash()          { return hash; }
    public Committer           getCommitter()     { return committer; }
    public String              getMsg()           { return msg; }
    public List<Modification>  getModifications() { return modifications; }
}

class Committer {
    private final String nome;
    Committer(String nome) { this.nome = nome; }
    public String getNome() { return nome; }
}

// ─── Modification (p.142) ────────────────────────────────────────────────────

/**
 * Aniche, p.142 — código exato do livro.
 */
class Modification {
    private String           oldPath;
    private String           newPath;
    private ModificationType type;
    private String           diff;
    private String           sourceCode;

    Modification(String path, ModificationType type, String sourceCode) {
        this.newPath    = path;
        this.oldPath    = path;
        this.type       = type;
        this.sourceCode = sourceCode;
        this.diff       = "";
    }

    public String           getOldPath()    { return oldPath; }
    public String           getNewPath()    { return newPath; }
    public ModificationType getType()       { return type; }
    public String           getDiff()       { return diff; }
    public String           getSourceCode() { return sourceCode; }
}

enum ModificationType { ADD, MODIFY, DELETE, RENAME, COPY }

// ─── CommitVisitor (p.139) ───────────────────────────────────────────────────

/**
 * Aniche, p.139 — código exato do livro:
 *
 * public interface CommitVisitor {
 *     void process(SCMRepository repo, Commit commit, PersistenceMechanism writer);
 *     String name();
 * }
 */
interface CommitVisitor {
    void process(SCMRepository repo, Commit commit, PersistenceMechanism writer);
    String name();
}

// ─── PersistenceMechanism (p.140) ────────────────────────────────────────────

/**
 * Aniche, p.140 — código exato do livro:
 *
 * public interface PersistenceMechanism {
 *     void write(Object... line);
 *     void close();
 * }
 */
interface PersistenceMechanism {
    void write(Object... line);
    void close();
}

// ─── ContadorDeClassesRemovidas (p.139) ─────────────────────────────────────

/**
 * Aniche, p.139-140 — visitante exato do livro.
 * Conta classes deletadas em um commit.
 */
class ContadorDeClassesRemovidas implements CommitVisitor {

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        int qtdDeDelecoes = 0;
        for (Modification m : commit.getModifications()) {
            if (m.getType().equals(ModificationType.DELETE)) {
                qtdDeDelecoes++;
            }
        }

        writer.write(
                repo.getLastDir(),
                commit.getHash(),
                qtdDeDelecoes);
    }

    @Override
    public String name() { return "ContadorDeClassesRemovidas"; }
}

// ─── ClassLevelMetric e Factory (p.143) ──────────────────────────────────────

/**
 * Aniche, p.143 — interfaces exatas do livro.
 */
interface ClassLevelMetricFactory {
    ClassLevelMetric build();
    String getName();
}

interface ClassLevelMetric {
    double calculate(String sourceCode);
}

// ─── CSVFile (implementação simples de PersistenceMechanism) ─────────────────

/**
 * Referenciado no livro, p.140.
 * Em produção escreveria em arquivo CSV.
 */
class CSVFile implements PersistenceMechanism {
    private final String arquivo;
    private final StringBuilder buffer = new StringBuilder();
    private int linhas = 0;

    CSVFile(String arquivo) { this.arquivo = arquivo; }

    @Override
    public void write(Object... line) {
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < line.length; i++) {
            if (i > 0) row.append(",");
            row.append(line[i]);
        }
        buffer.append(row).append("\n");
        linhas++; // Incrementa o contador de linhas
        System.out.println("    [CSV:" + arquivo + "] " + row);
    }

    @Override
    public void close() {
        System.out.println("    [CSV:" + arquivo + "] fechado — " + linhas + " linhas");
    }

    public String getConteudo() { return buffer.toString(); }
    public int getLinhas() { return linhas; }
}
