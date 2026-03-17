package capitulo9_maus_cheiros.v6_shotgun_surgery;

/**
 * ════════════════════════════════════════════════════════════════
 * MAUS CHEIROS — Shotgun Surgery
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 9, seção 9.6:
 * "Sabe quando seu usuário pede uma mudança no sistema e, para que isso
 *  aconteça, você precisa modificar 20 arquivos de uma só vez?"
 *
 * Diferença entre Divergent Changes e Shotgun Surgery:
 *
 *   Divergent Changes  → uma classe, muitas razões para mudar
 *                         (problema de coesão dentro da classe)
 *
 *   Shotgun Surgery    → uma mudança, muitas classes a editar
 *                         (problema de responsabilidade espalhada pelo sistema)
 *
 * Cause raiz: abstração insuficiente — um conceito não tem casa.
 * A taxa da guilda, por exemplo, está hardcoded em 10 lugares.
 * Mudar a taxa = cirurgia de espingarda em 10 arquivos.
 */

// ─── PROBLEMA ────────────────────────────────────────────────────────────────

/**
 * ❌ A taxa da Guilda (15%) está espalhada por todo o sistema.
 *    Mudar a taxa = grep + edição manual em todos esses lugares.
 *    Com certeza algum será esquecido.
 */
class ProcessadorDeContratoComShotgun {
    public double calcularRecompensaLiquida(double bruta) {
        return bruta - (bruta * 0.15); // ❌ taxa hardcoded aqui
    }
}

class RelatorioDeContratoComShotgun {
    public String gerarLinha(String monstro, double bruta) {
        double taxa    = bruta * 0.15; // ❌ taxa hardcoded aqui também
        double liquida = bruta - taxa;
        return String.format("%-20s | Bruta: R$%.2f | Taxa: R$%.2f | Líquida: R$%.2f",
                monstro, bruta, taxa, liquida);
    }
}

class AuditoriaDeContratoComShotgun {
    public boolean taxaEstaCorreta(double bruta, double liquida) {
        double taxaEsperada = bruta * 0.15; // ❌ taxa hardcoded aqui também
        return (bruta - liquida) == taxaEsperada;
    }
}

// ─── SOLUÇÃO: Centralizar o conceito ─────────────────────────────────────────

/**
 * ✅ TaxaDaGuilda — o conceito tem uma casa.
 *    Mudar a taxa? Um único lugar.
 *    Adicionar lógica contextual (ex: taxa menor para veteranos)? Um único lugar.
 */
class TaxaDaGuilda {
    // ✅ Constante nomeada — semântica e centralizada
    private static final double PERCENTUAL_PADRAO = 0.15;

    public double calcular(double recompensaBruta) {
        return recompensaBruta * PERCENTUAL_PADRAO;
    }

    public double calcularLiquida(double recompensaBruta) {
        return recompensaBruta - calcular(recompensaBruta);
    }

    public boolean taxaEstaCorreta(double bruta, double liquida) {
        return Math.abs((bruta - liquida) - calcular(bruta)) < 0.001;
    }

    public double getPercentual() { return PERCENTUAL_PADRAO; }
}

/**
 * ✅ Todas as classes dependem de TaxaDaGuilda — não de um número mágico.
 */
class ProcessadorDeContratoLimpo {
    private final TaxaDaGuilda taxa;
    ProcessadorDeContratoLimpo(TaxaDaGuilda taxa) { this.taxa = taxa; }

    public double calcularRecompensaLiquida(double bruta) {
        return taxa.calcularLiquida(bruta); // ✅ sem hardcode
    }
}

class RelatorioDeContratoLimpo {
    private final TaxaDaGuilda taxa;
    RelatorioDeContratoLimpo(TaxaDaGuilda taxa) { this.taxa = taxa; }

    public String gerarLinha(String monstro, double bruta) {
        double t       = taxa.calcular(bruta);
        double liquida = taxa.calcularLiquida(bruta);
        return String.format("%-20s | Bruta: R$%.2f | Taxa: R$%.2f | Líquida: R$%.2f",
                monstro, bruta, t, liquida);
    }
}

class AuditoriaDeContratoLimpo {
    private final TaxaDaGuilda taxa;
    AuditoriaDeContratoLimpo(TaxaDaGuilda taxa) { this.taxa = taxa; }

    public boolean taxaEstaCorreta(double bruta, double liquida) {
        return taxa.taxaEstaCorreta(bruta, liquida); // ✅ sem hardcode
    }
}

