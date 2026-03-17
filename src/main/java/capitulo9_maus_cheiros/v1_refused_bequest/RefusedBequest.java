package capitulo9_maus_cheiros.v1_refused_bequest;

/**
 * ════════════════════════════════════════════════════════════════
 * MAUS CHEIROS — Refused Bequest
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 9, seção 9.1:
 * "Refused Bequest é o nome dado para quando herdamos de uma classe,
 *  mas não queremos fazer uso de alguns dos métodos herdados."
 *
 * "Bequest" = herança/legado. "Refused" = recusado.
 * O filho recebe a herança, mas rejeita parte dela.
 *
 * No Witcher: EscolaDeAlquimia herda de EscolaDeGuerra para reaproveitar
 * getName() e getReputation(). Mas ela não treina combate — então o que
 * fazer com treinarCombate() e calcularBonusDeBatalha()?
 *
 * ❌ Jogar exceção → quebra LSP
 * ❌ Retornar 0/null → comportamento silenciosamente errado
 * ✅ Solução: composição ou herança somente onde a relação "é um" é real
 */

// ─── PROBLEMA ────────────────────────────────────────────────────────────────

class EscolaDeGuerraBase {
    protected final String nome;
    protected int reputacao;

    public EscolaDeGuerraBase(String nome, int reputacao) {
        this.nome = nome;
        this.reputacao = reputacao;
    }

    public String getNome() { return nome; }
    public int getReputacao() { return reputacao; }

    public void treinarCombate() { System.out.println(nome + " treina combate"); }
    public int calcularBonusDeBatalha() { return reputacao * 2; }
    public String gerarInsignia() { return "⚔ " + nome.toUpperCase();}
}

/**
 * ❌ EscolaDeAlquimia herda de EscolaDeGuerraBase apenas para reaproveitar
 *    getNome() e getReputacao(). Ela NÃO treina combate.
 *    Resultado: métodos herdados que são recusados.
 */
class EscolaDeAlquimiaComSmell extends EscolaDeGuerraBase {

    EscolaDeAlquimiaComSmell(String nome, int reputacao) { super(nome, reputacao); }

    @Override
    public void treinarCombate() {
        // ❌ Opção 1: exceção — quebra LSP, surpresa em runtime
        throw new UnsupportedOperationException("Escola de Alquimia não treina combate!");
    }

    @Override
    public int calcularBonusDeBatalha() {
        // ❌ Opção 2: retorna 0 — silencioso, difícil de detectar
        return 0;
    }

    public void fabricarPocao(String nome) { System.out.println(this.nome + " fabrica: " + nome); }
}

// ─── SOLUÇÃO: Composição ─────────────────────────────────────────────────────

/**
 * ✅ Interface mínima com apenas o que todas as escolas compartilham.
 */
interface Escola {
    String getNome();
    int getReputacao();
    String gerarInsignia();
}

/**
 * ✅ EscolaDeGuerraReal implementa Escola + suas capacidades próprias.
 *    Sem métodos inventados que outros não querem.
 */
class EscolaDeGuerraReal implements Escola {
    private final String nome;
    private final int reputacao;

    EscolaDeGuerraReal(String nome, int reputacao) {
        this.nome      = nome;
        this.reputacao = reputacao;
    }

    @Override public String getNome()       { return nome; }
    @Override public int    getReputacao()  { return reputacao; }
    @Override public String gerarInsignia() { return "⚔ " + nome.toUpperCase(); }

    public void treinarCombate()         { System.out.println(nome + " treina combate"); }
    public int calcularBonusDeBatalha()  { return reputacao * 2; }
}

/**
 * ✅ EscolaDeAlquimiaReal implementa Escola sem herdar nada que não quer.
 *    Não existe treinarCombate() nem calcularBonusDeBatalha() aqui.
 */
class EscolaDeAlquimiaReal implements Escola {
    private final String nome;
    private final int reputacao;

    EscolaDeAlquimiaReal(String nome, int reputacao) {
        this.nome      = nome;
        this.reputacao = reputacao;
    }

    @Override public String getNome()       { return nome; }
    @Override public int    getReputacao()  { return reputacao; }
    @Override public String gerarInsignia() { return "⚗ " + nome.toUpperCase(); }

    public void fabricarPocao(String nome)  {
        System.out.println(this.nome + " fabrica: " + nome);
    }

    public int calcularPotenciaDaFormula()  { return reputacao * 3; }
}