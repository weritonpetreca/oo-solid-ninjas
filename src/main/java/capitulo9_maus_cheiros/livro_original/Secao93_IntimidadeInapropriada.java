package capitulo9_maus_cheiros.livro_original;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 9, seção 9.3: Intimidade Inapropriada
// ══════════════════════════════════════════════════════════════════════════════

/**
 * Aniche, p.122:
 * "É algo bem parecido [com Feature Envy]. Imagine outra classe conhecendo
 *  e/ou alterando detalhes internos da sua classe? Problemático e já sabemos
 *  o porquê: a falta de encapsulamento faz com que o desenvolvedor precise
 *  fazer a mesma alteração em diferentes pontos."
 */

// ─── NotaFiscal do exemplo de intimidade ─────────────────────────────────────

/**
 * NotaFiscal usada no exemplo de Intimidade Inapropriada.
 * Aniche, p.122-123.
 */
class NotaFiscalIntimidade {
    private final double valor;
    private boolean encecrrada;
    private boolean importante;

    public NotaFiscalIntimidade(double valor) {
        this.valor = valor;
        this.encecrrada = false;
    }

    public double getValor() { return valor; }
    public boolean isEncerrada() { return encecrrada; }

    public void encerrar() { this.encecrrada = true; }
    public void marcaComoImportante() { this.importante = true; }
    public boolean isImportante() { return importante; }
}

// ─── PROBLEMA: Intimidade Inapropriada ───────────────────────────────────────

/**
 * ❌ processa() — Intimidade Inapropriada.
 *
 * Aniche, p.122-123:
 * "Observe que o código faz perguntas para o objeto e, dada a resposta, ele
 *  decide marcá-la como importante. Essa é uma decisão que deveria estar dentro
 *  da nota fiscal."
 *
 * "Imagine que em algum outro ponto do seu sistema você criará e manipulará
 *  notas fiscais. E se você criar uma nota fiscal, encerrá-la, for maior que
 *  5000, e você esquecer de marcá-la como importante?"
 *
 * Código direto do livro, p.123:
 *   public void processa(NotaFiscal nf) {
 *       if(nf.isEncerrada() && nf.getValor() > 5000) {
 *           nf.marcaComoImportante();
 *       }
 *   }
 */
class ProcessadorComIntimidade {

    /**
     * ❌ Pergunta o estado interno de NotaFiscal para tomar uma decisão
     *    que deveria estar encapsulada dentro da própria nota.
     */
    public void processa(NotaFiscalIntimidade nf) {
        if (nf.isEncerrada() && nf.getValor() > 5000)
            nf.marcaComoImportante(); // ❌ decisão que deveria estar em NotaFiscal
    }
}

// ─── SOLUÇÃO: Tell, Don't Ask ────────────────────────────────────────────────

/**
 * ✅ NotaFiscalEncapsulada — a regra fica dentro do objeto.
 *
 * Aniche, p.123:
 * "Procure sempre colocar comportamentos nos lugares corretos, e não deixe
 *  suas abstrações vazarem. Encapsulamento é fundamental."
 */
class NotaFiscalEncapsulada {
    private final double valor;
    private boolean encerrada;
    private boolean importante;

    public NotaFiscalEncapsulada(double valor) {
        this.valor = valor;
        this.encerrada = false;
    }

    /**
     * ✅ A regra de "marcar como importante" vive dentro do objeto.
     *    Qualquer lugar que encerrar a nota terá o comportamento correto
     *    automaticamente — sem if espalhados pelo sistema.
     */
    public void encerrar() {
        this.encerrada = true;
        if (this.valor > 5000)
            this.importante = true; // regra encapsulada - um único lugar
    }

    public double getValor() { return valor; }
    public boolean isEncerrada() { return encerrada; }
    public boolean isImportante() { return importante; }
}

/**
 * ✅ ProcessadorLimpo — apenas diz o que fazer, não pergunta estado interno.
 */
class ProcessadorLimpo {
    public void processa(NotaFiscalEncapsulada nf) { nf.encerrar(); } // ✅ Tell, don't ask
}