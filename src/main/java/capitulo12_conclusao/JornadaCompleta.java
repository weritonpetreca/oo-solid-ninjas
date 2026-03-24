package capitulo12_conclusao;

/**
 * ════════════════════════════════════════════════════════════════
 * CAPÍTULO 12 — Conclusão
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, p.147:
 * "Fazer um bom projeto de classes orientado a objeto não é fácil.
 *  É muita coisa para pensar. É pensar em ter classes com responsabilidades
 *  bem definidas e, assim, ganhar em coesão. É pensar em ter classes com
 *  dependências bem pensadas e estáveis, e dessa forma ganhar em
 *  flexibilidade. É pensar em esconder os detalhes de implementação
 *  dentro de cada classe, e ganhar a propagação boa de mudança."
 *
 * "Fazer tudo isso requer prática e experiência. Lembre-se também que
 *  você não criará um bom projeto de classes de primeira; valide, aprenda
 *  e melhore o seu projeto de classes atual o tempo todo."
 *
 * Este capítulo não possui código original no livro — é uma conclusão
 * reflexiva. A versão Witcher o implementa como um "Mapa da Jornada":
 * uma síntese visual de todos os princípios aprendidos, demonstrando
 * como eles se interconectam numa única classe exemplo.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 🐺 A JORNADA COMPLETA DO BRUXO PROGRAMADOR
 *
 * Esta classe demonstra todos os princípios aprendidos ao longo do livro,
 * reunidos em um único exemplo coeso e coerente.
 *
 * Cada decisão de design está documentada com a referência ao capítulo
 * que a ensinou.
 */

// ─── Tiny Types (Cap. 8) ─────────────────────────────────────────────────────

record NomeDeBruxo(String valor) {
    NomeDeBruxo {
        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException("Nome do bruxo não pode ser vazio");
    }
}

record EscolaDeBruxo(String nome) {
    EscolaDeBruxo {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Escola não pode ser vazia");
    }
}

record RecompensaBruta(double valor) {
    RecompensaBruta {
        if (valor < 0) throw new IllegalArgumentException("Recompensa não pode ser negativa");
    }
}

// ─── Imutabilidade (Cap. 8) ───────────────────────────────────────────────────

record LocalizacaoAtual(String regiao, String cidade) {
    LocalizacaoAtual moverPara(String novaRegiao, String novaCidade) {
        return new LocalizacaoAtual(novaRegiao, novaCidade); // nova instância
    }
}

// ─── Interface magra / ISP (Cap. 7) ──────────────────────────────────────────

interface Tributavel {
    double calcularImposto();
}

// ─── Strategy / OCP (Cap. 4) ──────────────────────────────────────────────────

interface CalculadorDeTaxa {
    double calcular(double recompensaBruta);
}

record TaxaPadrao() implements CalculadorDeTaxa {
    @Override
    public double calcular(double bruta) { return bruta * 0.15; }
}

record TaxaVeterano() implements CalculadorDeTaxa {
    @Override
    public double calcular(double bruta) { return bruta * 0.10; }
}

record TaxaMestre() implements CalculadorDeTaxa {
    @Override
    public double calcular(double bruta) { return bruta * 0.05; }
}

// ─── Null Object (Cap. 8) ────────────────────────────────────────────────────

interface InformacaoDeContato {
    String enviarMensagem(String conteudo);
    boolean isDisponivel();
}

record MensageiroReal(String email) implements InformacaoDeContato {
    @Override public String enviarMensagem(String c) { return "Enviado para " + email + ": " + c; }
    @Override public boolean isDisponivel()          { return true; }
}

record MensageiroDesconhecido() implements InformacaoDeContato {
    @Override public String enviarMensagem(String c) { return "[sem contato] mensagem ignorada"; }
    @Override public boolean isDisponivel()          { return false; }
}

// ─── Classe principal — todos os princípios juntos ────────────────────────────

/**
 * 🐺 BruxoDoContinente — a síntese de toda a jornada.
 *
 * Cap. 1  → Orientação a Objetos: objeto com estado e comportamento
 * Cap. 2  → SRP: uma responsabilidade — representar um caçador
 * Cap. 3  → DIP: depende de interfaces (CalculadorDeTaxa, InformacaoDeContato)
 * Cap. 4  → OCP: nova taxa? Nova implementação, sem mexer aqui
 * Cap. 5  → Encapsulamento: lista imutável, setters apenas onde fazem sentido
 * Cap. 6  → Composição: "tem um" CalculadorDeTaxa, não herda taxa
 * Cap. 7  → ISP: aceita Tributavel — interface mínima
 * Cap. 8  → Tiny Types, Imutabilidade, Null Object, Construtor Rico
 * Cap. 9  → Ausência de code smells: coesão, sem God Class, sem Feature Envy
 * Cap. 10 → Boas métricas: CC baixa, LCOM ≈ 0, NOA moderado, CE razoável
 * Cap. 11 → Plugável como visitante num framework (CommitVisitor / VisitanteDeContrato)
 */
class BruxoDoContinente implements Tributavel {

    // Cap. 8 — Tiny Types e Construtor Rico
    private final NomeDeBruxo         nome;
    private final EscolaDeBruxo       escola;
    // Cap. 6 — Composição: CalculadorDeTaxa é plugável
    private final CalculadorDeTaxa    calculadorDeTaxa;
    // Cap. 8 — Null Object: nunca null
    private final InformacaoDeContato contato;
    // Cap. 8 — Imutabilidade: "mudança" retorna nova instância
    private LocalizacaoAtual          localizacao;
    // Cap. 5 — Lista protegida
    private final List<String>        missoesConcluidass = new ArrayList<>();
    private double                    recompensaAcumulada = 0.0;

    /**
     * Cap. 8 — Construtor Rico: atributos obrigatórios exigidos na criação.
     * Impossível criar um bruxo sem nome, escola e calculador de taxa.
     */
    BruxoDoContinente(NomeDeBruxo nome, EscolaDeBruxo escola,
                      CalculadorDeTaxa calculadorDeTaxa,
                      InformacaoDeContato contato,
                      LocalizacaoAtual localizacaoInicial) {
        this.nome             = nome;
        this.escola           = escola;
        this.calculadorDeTaxa = calculadorDeTaxa;
        this.contato          = contato;
        this.localizacao      = localizacaoInicial;
    }

    /**
     * Cap. 5 — Tell, Don't Ask: o bruxo sabe quando está pronto para aceitar missão.
     */
    boolean podeAceitarMissao() {
        return missoesConcluidass.size() < 10 || recompensaAcumulada < 50_000;
    }

    /**
     * Cap. 5/9 — Encapsulamento, Tell Don't Ask.
     * Cap. 2/9 — SRP: só registra missão concluída.
     */
    void registrarMissaoConcluida(String monstro, RecompensaBruta recompensa) {
        missoesConcluidass.add(monstro);
        double taxa    = calculadorDeTaxa.calcular(recompensa.valor());
        recompensaAcumulada += recompensa.valor() - taxa;
    }

    /**
     * Cap. 8 — Imutabilidade: mover cria nova LocalizacaoAtual.
     */
    void moverPara(String regiao, String cidade) {
        localizacao = localizacao.moverPara(regiao, cidade);
    }

    /**
     * Cap. 7 — Interface Segregation: implementa Tributavel (interface mínima).
     * Cap. 6 — Composição: delega cálculo para o CalculadorDeTaxa.
     */
    @Override
    public double calcularImposto() {
        return calculadorDeTaxa.calcular(recompensaAcumulada);
    }

    /**
     * Cap. 8 — Null Object: contato nunca é null, MensageiroDesconhecido assume.
     */
    String notificar(String mensagem) {
        return contato.enviarMensagem(mensagem);
    }

    // Cap. 5 — lista imutável externamente
    List<String>   getMissoesConcluidass()  { return Collections.unmodifiableList(missoesConcluidass); }
    NomeDeBruxo    getNome()                { return nome; }
    EscolaDeBruxo  getEscola()             { return escola; }
    LocalizacaoAtual getLocalizacao()      { return localizacao; }
    double         getRecompensaAcumulada(){ return recompensaAcumulada; }
    boolean        temContato()            { return contato.isDisponivel(); }
}

// ─── Repositório com Optional (Cap. 8) ───────────────────────────────────────

class RegistroDeBruxos {
    private final List<BruxoDoContinente> bruxos = new ArrayList<>();

    void registrar(BruxoDoContinente bruxo) { bruxos.add(bruxo); }

    // Cap. 8 — nunca retorna null: usa Optional
    Optional<BruxoDoContinente> buscarPorNome(String nome) {
        return bruxos.stream()
                .filter(b -> b.getNome().valor().equalsIgnoreCase(nome))
                .findFirst();
    }

    List<BruxoDoContinente> listarDisponiveis() {
        return bruxos.stream().filter(BruxoDoContinente::podeAceitarMissao).toList();
    }
}