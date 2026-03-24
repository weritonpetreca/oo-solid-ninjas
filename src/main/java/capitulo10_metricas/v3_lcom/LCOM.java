package capitulo10_metricas.v3_lcom;

import java.util.List;
import java.util.ArrayList;

/**
 * ════════════════════════════════════════════════════════════════
 * MÉTRICAS — Coesão e a LCOM (Lack of Cohesion of Methods)
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 10, seção 10.3:
 * "Se a classe for coesa, esses comportamentos manipulam boa parte desses
 *  atributos [...] É isso que a métrica conhecida por LCOM contabiliza."
 *
 * LCOM = 0 → máxima coesão (todos métodos compartilham atributos)
 * LCOM = 1 → mínima coesão (cada método usa só seus atributos — divida!)
 *
 * Aniche, p.131:
 * "Apesar de a métrica ter problemas [getters/setters inflam o número],
 *  lembre-se que ela é apenas uma heurística. Ela pode errar de vez em
 *  quando, mas provavelmente a classe que ela mostrar que é realmente
 *  pouco coesa pode ser mesmo problemática."
 */

// ─── PROBLEMA: Classe com LCOM alto ──────────────────────────────────────────

/**
 * ❌ GuildaComDuasResponsabilidades — LCOM ≈ 1.
 *
 * Grupo A (Financeiro): {recompensa, taxa}
 *   → usados por calcularLiquida(), calcularImposto()
 *
 * Grupo B (Reputação): {nome, totalMissoes, ranking}
 *   → usados por promover(), rebaixar(), gerarInsignia()
 *
 * Os dois grupos NÃO se cruzam → LCOM alto → DIVIDIR!
 */
class GuildaComDuasResponsabilidades {

    // Grupo A — Atributos Financeiros
    private double recompensa;
    private double taxa;

    // Grupo B — Atributos de Reputação
    private String nome;
    private int    totalMissoes;
    private String ranking;

    GuildaComDuasResponsabilidades(String nome, double recompensa, double taxa) {
        this.nome       = nome;
        this.recompensa = recompensa;
        this.taxa       = taxa;
        this.totalMissoes = 0;
        this.ranking    = "NOVATO";
    }

    // ─── Métodos do Grupo A — só usam recompensa e taxa ───────────────────

    public double calcularLiquida()  { return recompensa * (1 - taxa); }    // F: recompensa, taxa
    public double calcularImposto()  { return recompensa * taxa; }           // F: recompensa, taxa

    // ─── Métodos do Grupo B — só usam nome, totalMissoes, ranking ─────────

    public void promover() {                                                 // F: totalMissoes, ranking
        totalMissoes++;
        if (totalMissoes >= 10) ranking = "VETERANO";
        if (totalMissoes >= 25) ranking = "MESTRE";
    }

    public String gerarInsignia() { return ranking + " - " + nome; }        // F: ranking, nome
    public void rebaixar()        { ranking = "NOVATO"; totalMissoes = 0; } // F: ranking, totalMissoes

    // LCOM ≈ 1 — os dois grupos são completamente independentes
    // Sinal: você consegue dividir esta classe em duas sem quebrar nada
}

// ─── SOLUÇÃO: Duas classes coesas ────────────────────────────────────────────

/**
 * ✅ FinanceiroDeGuilda — LCOM ≈ 0.
 * Todos os métodos usam os mesmos atributos (recompensa, taxa).
 * Responsabilidade única: cálculos financeiros.
 */
class FinanceiroDeGuilda {
    private final double recompensa;
    private final double taxa;

    FinanceiroDeGuilda(double recompensa, double taxa) {
        this.recompensa = recompensa;
        this.taxa       = taxa;
    }

    public double calcularLiquida()  { return recompensa * (1 - taxa); }
    public double calcularImposto()  { return recompensa * taxa; }
    public boolean isPremium()       { return recompensa > 5000; }

    // LCOM ≈ 0 — todos métodos usam recompensa e/ou taxa → coesa!
}

/**
 * ✅ ReputacaoDeCacador — LCOM ≈ 0.
 * Todos os métodos manipulam nome, totalMissoes ou ranking.
 * Responsabilidade única: rastrear reputação.
 */
class ReputacaoDeCacador {
    private final String nome;
    private int    totalMissoes = 0;
    private String ranking      = "NOVATO";

    ReputacaoDeCacador(String nome) { this.nome = nome; }

    public void registrarMissao() {
        totalMissoes++;
        recalcularRanking();
    }

    private void recalcularRanking() {
        if (totalMissoes >= 25)      ranking = "MESTRE";
        else if (totalMissoes >= 10) ranking = "VETERANO";
        else if (totalMissoes >= 5)  ranking = "EXPERIENTE";
        else                          ranking = "NOVATO";
    }

    public String gerarInsignia()   { return ranking + " — " + nome; }
    public String getRanking()      { return ranking; }
    public int    getTotalMissoes() { return totalMissoes; }

    // LCOM ≈ 0 — todos métodos participam da reputação → coesa!
}

// ─── Calculadora didática de LCOM ────────────────────────────────────────────

/**
 * Calcula LCOM de forma simplificada.
 *
 * LCOM HS = 1 - (sum(MF) / (M * F))
 *   onde:
 *     MF = para cada atributo F, quantos métodos o acessam
 *     M  = total de métodos (ignorando getters/setters)
 *     F  = total de atributos
 *
 * Resultado entre 0 (coeso) e 1 (não coeso).
 */
class CalculadorLCOM {

    /**
     * @param m     número de métodos relevantes (excluindo getters/setters)
     * @param f     número de atributos
     * @param mfSum soma de "quantos métodos acessam cada atributo"
     * @return LCOM HS (0 = coeso, 1 = não coeso)
     */
    public double calcularLcomHS(int m, int f, int mfSum) {
        if (m == 0 || f == 0) return 0;
        double lcom = 1.0 - ((double) mfSum / ((double) m * f));
        return Math.max(0, Math.min(1, lcom));
    }

    public String avaliar(double lcom) {
        if (lcom <= 0.2)      return "✅ Alta coesão (LCOM ≈ 0)";
        else if (lcom <= 0.5) return "🟡 Coesão aceitável";
        else if (lcom <= 0.8) return "🟠 Considere dividir a classe";
        else                   return "🔴 Baixa coesão — divida a classe!";
    }
}