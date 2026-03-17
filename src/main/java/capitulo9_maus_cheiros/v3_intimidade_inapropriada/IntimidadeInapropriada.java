package capitulo9_maus_cheiros.v3_intimidade_inapropriada;

/**
 * ════════════════════════════════════════════════════════════════
 * MAUS CHEIROS — Intimidade Inapropriada
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 9, seção 9.3:
 * "Imagine outra classe conhecendo e/ou alterando detalhes internos
 *  da sua classe? Isso faz com que o desenvolvedor precise fazer a
 *  mesma alteração em diferentes pontos."
 *
 * Diferença entre Feature Envy e Intimidade Inapropriada:
 *   Feature Envy    → o método não usa nada da própria classe
 *   Intimidade Inapropriada → a classe CONHECE DEMAIS os internos de outra
 *
 * O código "faz perguntas" ao objeto para tomar decisões que
 * DEVERIAM estar dentro do próprio objeto.
 * Isso é o oposto de "Tell, don't ask."
 */

// ─── PROBLEMA ────────────────────────────────────────────────────────────────

class MissaoComSmell {
    private double valor;
    private boolean encerrada;
    private String nivelDeRisco; // "BAIXO", "ALTO", "EXTREMO"
    private boolean marcadaComoUrgente;

    MissaoComSmell(double valor, String nivelDeRisco) {
        this.valor        = valor;
        this.nivelDeRisco = nivelDeRisco;
        this.encerrada    = false;
    }

    // ❌ Getters que expõem o estado interno para quem tomar decisões fora
    public double  getValor()         { return valor; }
    public boolean isEncerrada()      { return encerrada; }
    public String  getNivelDeRisco()  { return nivelDeRisco; }
    public void    setEncerrada(boolean e) { this.encerrada = e; }
    public void    marcarComoUrgente()     { this.marcadaComoUrgente = true; }
    public boolean isMarcadaComoUrgente()  { return marcadaComoUrgente; }
}

/**
 * ❌ GerenciadorDeMissoesComSmell tem INTIMIDADE INAPROPRIADA com Missao.
 *    Ele faz perguntas ao objeto e toma decisões que deveriam estar em Missao.
 *
 *    Se a regra "missão urgente" mudar (ex: adicionar novo critério),
 *    é preciso encontrar TODOS os lugares que fazem esse if.
 *    Com certeza algum será esquecido.
 */
class GerenciadorDeMissoesComSmell {

    public void avaliarMissao(MissaoComSmell missao) {
        // ❌ Conhece demais: pergunta, decide, age — tudo fora do objeto
        if (!missao.isEncerrada() && missao.getValor() > 5000) {
            missao.marcarComoUrgente();
            System.out.println("    ❌ [SMELL] Marcando missão urgente do lado de fora...");
        }
    }

    public double calcularPrioridade(MissaoComSmell missao) {
        // ❌ Lógica de negócio de Missao espalhada aqui
        if ("EXTREMO".equals(missao.getNivelDeRisco())) return missao.getValor() * 2.0;
        if ("ALTO".equals(missao.getNivelDeRisco()))    return missao.getValor() * 1.5;
        return missao.getValor();
    }
}

// ─── SOLUÇÃO: Tell, don't ask ─────────────────────────────────────────────────

/**
 * ✅ Missao encapsula suas próprias regras de negócio.
 *    Em vez de responder perguntas, ela executa comportamentos.
 *    "Tell, don't ask."
 */
class Missao {
    private final double valor;
    private final String nivelDeRisco;
    private boolean ativa;
    private boolean encerrada;
    private boolean urgente;

    Missao(double valor, String nivelDeRisco) {
        this.valor        = valor;
        this.nivelDeRisco = nivelDeRisco;
        this.ativa = false;
        this.encerrada    = false;
    }

    /**
     * ✅ A missão sabe quando deve ser marcada como urgente.
     *    A regra vive aqui — se mudar, muda em um só lugar.
     */
    public void encerrar() {
        this.encerrada = true;
        if (valor > 5000) {
            this.urgente = true; // regra encapsulada
        }
    }

    public void aceitar() {
        this.ativa = true;
    }

    /**
     * ✅ A missão calcula sua própria prioridade.
     *    Ninguém de fora precisa conhecer nivelDeRisco.
     */
    public double calcularPrioridade() {
        return switch (nivelDeRisco) {
            case "EXTREMO" -> valor * 2.0;
            case "ALTO"    -> valor * 1.5;
            default        -> valor;
        };
    }

    public boolean isUrgente()    { return urgente; }
    public boolean isEncerrada()  { return encerrada; }
    public double  getValor()     { return valor; }
}

/**
 * ✅ GerenciadorDeMissoes limpo — apenas orquestra, sem conhecer internos.
 */
class GerenciadorDeMissoesLimpo {

    public void encerrarMissao(Missao missao) {
        missao.encerrar(); // ✅ Tell, don't ask
        if (missao.isUrgente()) {
            System.out.println("    ✅ Missão marcada como urgente pela própria regra interna.");
        }
    }

    public void exibirPrioridade(Missao missao) {
        // ✅ Pergunta o resultado, não os dados para calcular
        System.out.printf("    ✅ Prioridade calculada: R$%.2f%n", missao.calcularPrioridade());
    }
}