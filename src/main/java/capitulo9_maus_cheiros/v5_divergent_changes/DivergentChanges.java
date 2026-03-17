package capitulo9_maus_cheiros.v5_divergent_changes;

/**
 * ════════════════════════════════════════════════════════════════
 * MAUS CHEIROS — Divergent Changes
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 9, seção 9.5:
 * "Divergent changes é o nome do mau cheiro para quando a classe não é
 *  coesa, e sofre alterações constantes, devido às suas diversas
 *  responsabilidades."
 *
 * Sinal: você olha o histórico de commits e a mesma classe foi alterada
 * por motivos completamente diferentes:
 *   - "Corrigindo cálculo de imposto"
 *   - "Ajustando formato do relatório"
 *   - "Mudando regra de notificação"
 *   - "Adicionando novo tipo de monstro"
 *
 * Todos esses commits na mesma classe = Divergent Changes.
 */

// ─── PROBLEMA ────────────────────────────────────────────────────────────────

/**
 * ❌ ProcessadorDeMissaoDivergente — muda por pelo menos 4 razões diferentes:
 *   1. Regra de imposto muda → altera calcularImposto()
 *   2. Formato do relatório muda → altera gerarRelatorio()
 *   3. Canal de notificação muda → altera notificar()
 *   4. Tipo de monstro novo → altera classificarMonstro()
 *
 * Cada mudança afeta a classe inteira e pode introduzir bugs
 * nas outras responsabilidades.
 */
class ProcessadorDeMissaoDivergente {
    private String nomeMonstro;
    private double recompensa;
    private String cacador;

    ProcessadorDeMissaoDivergente(String nomeMonstro, double recompensa, String cacador) {
        this.nomeMonstro = nomeMonstro;
        this.recompensa  = recompensa;
        this.cacador     = cacador;
    }

    // Responsabilidade 1: cálculo fiscal — muda quando lei tributária muda
    public double calcularImposto() { return recompensa * 0.15; }

    // Responsabilidade 2: apresentação — muda quando formato do relatório muda
    public String gerarRelatorio() {
        return String.format("=== MISSÃO ===\nMonstro: %s\nCaçador: %s\nValor: R$%.2f",
                nomeMonstro, cacador, recompensa);
    }

    // Responsabilidade 3: comunicação — muda quando canal de notificação muda
    public void notificar() {
        System.out.println("[CORVO] " + cacador + ": missão de " + nomeMonstro + " concluída");
    }

    // Responsabilidade 4: classificação — muda quando novos monstros são adicionados
    public String classificarMonstro() {
        if (nomeMonstro.contains("Grifo"))     return "Híbrido";
        if (nomeMonstro.contains("Strige"))    return "Vampiro";
        if (nomeMonstro.contains("Lobisomem")) return "Amaldiçoado";
        return "Desconhecido";
    }
}

// ─── SOLUÇÃO: Uma classe, uma razão para mudar ────────────────────────────────

record DadosDaMissao(String nomeMonstro, double recompensa, String cacador) {}

class CalculadorDeImpostoMissao {
    public double calcular(DadosDaMissao d) { return d.recompensa() * 0.15; }
}

class GeradorDeRelatorioMissao {
    public String gerar(DadosDaMissao d) {
        return String.format("=== MISSÃO ===\nMonstro: %s\nCaçador: %s\nValor: R$%.2f",
                d.nomeMonstro(), d.cacador(), d.recompensa());
    }
}

class NotificadorDeMissao {
    public void notificar(DadosDaMissao d) {
        System.out.println("[CORVO] " + d.cacador() + ": missão de " + d.nomeMonstro() + " concluída");
    }
}

class ClassificadorDeMonstro {
    public String classificar(String nomeMonstro) {
        if (nomeMonstro.contains("Grifo"))     return "Híbrido";
        if (nomeMonstro.contains("Strige"))    return "Vampiro";
        if (nomeMonstro.contains("Lobisomem")) return "Amaldiçoado";
        return "Desconhecido";
    }
}
