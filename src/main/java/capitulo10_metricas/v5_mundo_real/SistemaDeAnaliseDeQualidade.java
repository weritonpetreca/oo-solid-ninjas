package capitulo10_metricas.v5_mundo_real;

import java.util.*;

/**
 * ══════════════════════════════════════════════════════════════════════
 * CAP 10 — MUNDO REAL: O Sistema de Análise de Qualidade da Guilda
 * ══════════════════════════════════════════════════════════════════════
 *
 * Vesemir, o Arquiteto de Kaer Morhen, decide que a Guilda precisa de um
 * sistema que avalie a saúde do código dos contratos.
 *
 * Em vez de olhar todos os arquivos manualmente, ele criará um
 * "MetricMiner" simplificado: analisa classes do sistema e gera um
 * relatório de saúde com todas as métricas do Cap. 10.
 *
 * Conceitos aplicados:
 *   ✅ CC (Complexidade Ciclomática)
 *   ✅ NOA / NOM / NOP (Tamanho)
 *   ✅ LCOM HS simplificado (Coesão)
 *   ✅ CE / CA (Acoplamento Eferente/Aferente)
 *   ✅ Nomenclatura
 *   ✅ Limites customizados (não há número mágico — cada projeto define o seu)
 */

// ─── Modelo de domínio ────────────────────────────────────────────────────────

/**
 * Representa uma classe analisada com todas as suas métricas.
 * Tiny types (Cap. 8) em ação — cada métrica tem significado claro.
 */
class AnaliseDeClasse {
    private final String nome;
    private final int cc;     // Complexidade Ciclomática
    private final int noa;    // Number of Attributes
    private final int nom;    // Number of Methods
    private final int maxNop; // Max Number of Parameters
    private final double lcom; // LCOM HS (0–1)
    private final int ce;     // Acoplamento Eferente
    private final int ca;     // Acoplamento Aferente

    AnaliseDeClasse(String nome, int cc, int noa, int nom, int maxNop,
                    double lcom, int ce, int ca) {
        this.nome   = nome;
        this.cc     = cc;
        this.noa    = noa;
        this.nom    = nom;
        this.maxNop = maxNop;
        this.lcom   = lcom;
        this.ce     = ce;
        this.ca     = ca;
    }

    String getNome()   { return nome;   }
    int    getCc()     { return cc;     }
    int    getNoa()    { return noa;    }
    int    getNom()    { return nom;    }
    int    getMaxNop() { return maxNop; }
    double getLcom()   { return lcom;   }
    int    getCe()     { return ce;     }
    int    getCa()     { return ca;     }
}

// ─── Limites customizados (Aniche, p.134) ────────────────────────────────────

/**
 * Aniche, p.134:
 * "Você pode criar seu número mágico. Uma ideia um pouco melhor talvez
 *  seja ter seu próprio número [...] você não está comparando com um
 *  número ideal geral, mas sim com aquilo que você já está acostumado
 *  a manter."
 */
class LimitesDeQualidade {

    // ✅ Limites definidos pela própria equipe — não um número mágico externo
    private final int maxCC;
    private final int maxNOA;
    private final int maxNOM;
    private final int maxNOP;
    private final double maxLCOM;
    private final int maxCE;

    /** Limites padrão baseados na literatura e na experiência da Guilda. */
    LimitesDeQualidade() {
        this(10, 7, 15, 5, 0.7, 10);
    }

    LimitesDeQualidade(int maxCC, int maxNOA, int maxNOM, int maxNOP,
                       double maxLCOM, int maxCE) {
        this.maxCC   = maxCC;
        this.maxNOA  = maxNOA;
        this.maxNOM  = maxNOM;
        this.maxNOP  = maxNOP;
        this.maxLCOM = maxLCOM;
        this.maxCE   = maxCE;
    }

    int    getMaxCC()   { return maxCC;   }
    int    getMaxNOA()  { return maxNOA;  }
    int    getMaxNOM()  { return maxNOM;  }
    int    getMaxNOP()  { return maxNOP;  }
    double getMaxLCOM() { return maxLCOM; }
    int    getMaxCE()   { return maxCE;   }
}

// ─── Avaliador de cada classe ─────────────────────────────────────────────────

/**
 * Avalia uma classe contra os limites definidos pela equipe.
 * Retorna uma lista de violações encontradas.
 */
class AvaliadorDeClasse {

    private final LimitesDeQualidade limites;

    AvaliadorDeClasse(LimitesDeQualidade limites) {
        this.limites = limites;
    }

    public List<String> avaliar(AnaliseDeClasse analise) {
        var violacoes = new ArrayList<String>();

        if (analise.getCc() > limites.getMaxCC())
            violacoes.add("CC=" + analise.getCc()
                    + " (limite " + limites.getMaxCC() + ") → método(s) complexo(s)");

        if (analise.getNoa() > limites.getMaxNOA())
            violacoes.add("NOA=" + analise.getNoa()
                    + " (limite " + limites.getMaxNOA() + ") → possível God Class");

        if (analise.getNom() > limites.getMaxNOM())
            violacoes.add("NOM=" + analise.getNom()
                    + " (limite " + limites.getMaxNOM() + ") → responsabilidades demais");

        if (analise.getMaxNop() > limites.getMaxNOP())
            violacoes.add("MaxNOP=" + analise.getMaxNop()
                    + " (limite " + limites.getMaxNOP() + ") → use Tiny Types ou Builder");

        if (analise.getLcom() > limites.getMaxLCOM())
            violacoes.add(String.format("LCOM=%.2f (limite %.2f) → divida esta classe!",
                    analise.getLcom(), limites.getMaxLCOM()));

        if (analise.getCe() > limites.getMaxCE())
            violacoes.add("CE=" + analise.getCe()
                    + " (limite " + limites.getMaxCE() + ") → muitas dependências → fragil");

        return violacoes;
    }

    public boolean estaEmConformidade(AnaliseDeClasse analise) {
        return avaliar(analise).isEmpty();
    }
}

// ─── Gerador de relatório ─────────────────────────────────────────────────────

/**
 * Aniche, p.134:
 * "Você pode também pensar em avaliar seu sistema como um todo [...].
 *  Se você perceber que 90% das classes estão dentro dos limites aceitáveis,
 *  você pode dizer que esse sistema é ótimo. Se apenas 70%, ele não é ótimo,
 *  é apenas bom."
 */
class RelatorioDeQualidade {

    private final LimitesDeQualidade limites;

    RelatorioDeQualidade(LimitesDeQualidade limites) {
        this.limites = limites;
    }

    public void imprimir(List<AnaliseDeClasse> classes) {
        var avaliador = new AvaliadorDeClasse(limites);

        long emConformidade = classes.stream().filter(avaliador::estaEmConformidade).count();
        double porcentagem  = (double) emConformidade / classes.size() * 100;

        System.out.println("    " + "═".repeat(65));
        System.out.println("    RELATÓRIO DE QUALIDADE DA GUILDA DO CONTINENTE");
        System.out.println("    " + "═".repeat(65));
        System.out.printf("    Classes analisadas: %d | Conformes: %d | %.1f%% de saúde%n",
                classes.size(), emConformidade, porcentagem);

        String saude = porcentagem >= 90 ? "✅ ÓTIMO"
                : porcentagem >= 70 ? "🟡 BOM"
                : porcentagem >= 50 ? "🟠 ATENÇÃO"
                : "🔴 CRÍTICO";
        System.out.println("    Status geral: " + saude);
        System.out.println("    " + "─".repeat(65));

        for (var cls : classes) {
            var violacoes = avaliador.avaliar(cls);
            if (violacoes.isEmpty()) {
                System.out.printf("    ✅ %-40s — OK%n", cls.getNome());
            } else {
                System.out.printf("    🔴 %-40s — %d violação(ões):%n", cls.getNome(), violacoes.size());
                for (var v : violacoes) {
                    System.out.println("         → " + v);
                }
            }
        }
        System.out.println("    " + "─".repeat(65));
        System.out.println("    Próximos passos: priorize as classes com mais violações.");
        System.out.println("    Comece pelos erros de LCOM e CC — eles têm maior impacto.");
        System.out.println("    " + "═".repeat(65));
    }
}