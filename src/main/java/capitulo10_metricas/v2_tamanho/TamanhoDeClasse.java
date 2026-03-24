package capitulo10_metricas.v2_tamanho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * MÉTRICAS — Tamanho de Métodos e Classes
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 10, seção 10.2:
 * "Uma classe com 70 atributos provavelmente é mais difícil de ser mantida."
 * "Uma classe com 80 métodos provavelmente é difícil de ser mantida e faz
 *  coisa demais."
 *
 * Métricas:
 *   NOA  — Number of Attributes (atributos da classe)
 *   NOM  — Number of Methods    (métodos da classe)
 *   NOP  — Number of Parameters (parâmetros de um método)
 *   LOC  — Lines of Code        (linhas de código)
 *   MLOC — Method Lines of Code (linhas por método)
 */

// ─── PROBLEMA: Classe e método com métricas péssimas ─────────────────────────

/**
 * ❌ RegistroDeGuildaGigante — NOA=9, NOM muito alto, NOP alto.
 * É a God Class do capítulo 9 vista pelo ângulo das métricas.
 * Cada métrica gritando: "Quebre-me em classes menores!"
 */
class RegistroDeGuildaGigante {

    // NOA = 9 — muitos atributos → muitas responsabilidades
    private String nomeDoRegistrado;
    private String escola;
    private String credencial;
    private String localizacao;
    private double recompensaAcumulada;
    private int    totalMissoes;
    private String statusContrato;
    private String nomeDoGerente;
    private double taxaDeGuilda;

    /**
     * ❌ NOP = 9 — construtor difícil de usar.
     * Fácil de passar args na ordem errada.
     * Tiny Types (Cap. 8) resolveria isso.
     */
    public RegistroDeGuildaGigante(
            String nomeDoRegistrado, String escola, String credencial,
            String localizacao, double recompensaAcumulada, int totalMissoes,
            String statusContrato, String nomeDoGerente, double taxaDeGuilda) {
        this.nomeDoRegistrado   = nomeDoRegistrado;
        this.escola             = escola;
        this.credencial         = credencial;
        this.localizacao        = localizacao;
        this.recompensaAcumulada = recompensaAcumulada;
        this.totalMissoes       = totalMissoes;
        this.statusContrato     = statusContrato;
        this.nomeDoGerente      = nomeDoGerente;
        this.taxaDeGuilda       = taxaDeGuilda;
    }

    /**
     * ❌ MLOC = 25+ linhas, CC = 7, NOP = 4.
     * Tudo errado ao mesmo tempo.
     */
    public String processarMissao(String monstro, double recompensa,
                                  boolean urgente, String regiao) {
        String resultado = "";
        if (monstro == null || monstro.isBlank()) {
            return "ERRO: monstro obrigatório";
        }
        if (recompensa <= 0) {
            return "ERRO: recompensa inválida";
        }
        double taxa = recompensa * this.taxaDeGuilda;
        double liquida = recompensa - taxa;
        this.recompensaAcumulada += liquida;
        this.totalMissoes++;

        if (urgente) {
            resultado = "[URGENTE] ";
        }
        if (liquida > 5000) {
            resultado += "MISSÃO DE ALTO VALOR";
            this.statusContrato = "ATIVO_PREMIUM";
        } else if (liquida > 1000) {
            resultado += "MISSÃO PADRÃO";
            this.statusContrato = "ATIVO";
        } else {
            resultado += "MISSÃO SIMPLES";
        }
        resultado += " | " + monstro + " | " + regiao;
        return resultado;
        // MLOC ≈ 28, CC ≈ 7, NOP = 4 → candidata urgente a refatoração
    }

    public String getNomeDoRegistrado() { return nomeDoRegistrado; }
    public double getRecompensaAcumulada() { return recompensaAcumulada; }
    public int getTotalMissoes() { return totalMissoes; }
}

// ─── SOLUÇÃO: Classes pequenas com boas métricas ─────────────────────────────

/**
 * ✅ IdentidadeDeCacador — NOA=3, construtor focado.
 * Responsabilidade única: representar quem é o caçador.
 */
class IdentidadeDeCacador {
    private final String nome;
    private final String escola;
    private final String credencial;

    IdentidadeDeCacador(String nome, String escola, String credencial) {
        this.nome       = nome;
        this.escola     = escola;
        this.credencial = credencial;
    }

    @Override public String toString() { return nome + " [" + credencial + "] — " + escola; }
    String getNome() { return nome; }
}

/**
 * ✅ CarteiraDeRecompensas — NOA=2, métodos pequenos.
 * Responsabilidade única: rastrear recompensas financeiras.
 */
class CarteiraDeRecompensas {
    private double acumulado = 0.0;
    private int    missoes   = 0;

    void registrar(double recompensaLiquida) {
        acumulado += recompensaLiquida; // MLOC = 1
        missoes++;                       // MLOC = 1
    }

    double getAcumulado() { return acumulado; }
    int    getMissoes()   { return missoes;   }
}

/**
 * ✅ ProcessadorDeMissao — NOM=2, MLOC pequeno.
 * Responsabilidade única: processar o resultado de uma missão.
 */
class ProcessadorDeMissao {

    private static final double TAXA_PREMIUM = 5000.0;
    private static final double TAXA_PADRAO  = 1000.0;

    /**
     * ✅ CC = 4, NOP = 2, MLOC ≈ 8.
     * Clareza máxima, cobertura de testes fácil.
     */
    public String classificar(double recompensaLiquida, boolean urgente) {
        String prefixo = urgente ? "[URGENTE] " : "";
        if (recompensaLiquida > TAXA_PREMIUM) return prefixo + "MISSÃO DE ALTO VALOR";
        if (recompensaLiquida > TAXA_PADRAO)  return prefixo + "MISSÃO PADRÃO";
        return prefixo + "MISSÃO SIMPLES";
    }

    public double calcularLiquida(double bruta, double taxa) {
        return bruta - (bruta * taxa); // MLOC = 1
    }
}

// ─── Calculadora de métricas de tamanho ──────────────────────────────────────

/**
 * Relatório de métricas de tamanho de uma classe.
 * Em produção seria gerado pelo SonarQube ou PMD.
 */
record MetricasDeTamanho(
        String nomeClasse,
        int noa,   // Number of Attributes
        int nom,   // Number of Methods
        int maxNop // Max Number of Parameters em qualquer método
) {
    String avaliar() {
        var problemas = new ArrayList<String>();
        if (noa  > 7)  problemas.add("NOA=" + noa + " (> 7 → possível God Class)");
        if (nom  > 20) problemas.add("NOM=" + nom + " (> 20 → responsabilidades demais)");
        if (maxNop > 5) problemas.add("MaxNOP=" + maxNop + " (> 5 → use Tiny Types ou Builder)");
        return problemas.isEmpty() ? "✅ Saudável" : "🔴 " + String.join(" | ", problemas);
    }
}