package capitulo9_maus_cheiros.livro_original;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 9, seção 9.1: Refused Bequest
// ══════════════════════════════════════════════════════════════════════════════

/**
 * Aniche, p.120:
 * "Refused Bequest é o nome dado para quando herdamos de uma classe,
 *  mas não queremos fazer uso de alguns dos métodos herdados. Ou seja,
 *  a classe filho só precisa usar partes da classe pai."
 *
 * "Esse mau cheiro fica ainda pior quando a classe herda da classe pai,
 *  mas não quer, de forma alguma, ser tratada pela abstração."
 */

// ─── PROBLEMA: NotaFiscal herda de Matematica ────────────────────────────────

/**
 * ❌ Matematica — classe pai com métodos matemáticos.
 * Código direto do livro, p.120.
 */
class Matematica {
    public int quadrado(int a, int b) { return a * b; } //simplificação didática

    public int raiz(int a) { return (int) Math.sqrt(a); }
}

/**
 * ❌ NotaFiscal extends Matematica — Refused Bequest clássico.
 *
 * Aniche, p.120:
 * "Se temos uma classe NotaFiscal que herda da classe Matematica,
 *  simplesmente porque ela tem métodos úteis para ela, se em algum lugar
 *  passarmos uma referência de nota fiscal para alguém que quer uma classe
 *  de matemática, ela provavelmente se comportará mal."
 *
 * Código do livro:
 *   class NotaFiscal extends Matematica {
 *       public double calculaImposto() {
 *           return quadrado(this.valor, this.qtd); // reutiliza método do pai
 *       }
 *   }
 */
class NotaFiscalComSmell extends Matematica {
     private final double valor;
     private final int qtd;

    public NotaFiscalComSmell(double valor, int qtd) {
        this.valor = valor;
        this.qtd = qtd;
    }

    public double calculaImposto() {
        // ❌ Usa quadrado() do pai — herança só para reúso de método
        return quadrado((int) this.valor,this.qtd) * 0.1;
    }
}

/**
 * ❌ algumComportamento espera Matematica — mas pode receber NotaFiscal.
 *
 * Aniche, p.120:
 *   NotaFiscal nf = new NotaFiscal();
 *   algumComportamento(nf);
 *
 *   public void algumComportamento(Matematica m) {
 *       // aqui pode vir uma nota fiscal, mas
 *       // não queremos uma nota fiscal...
 *       // queremos matematica!
 *   }
 */
class SistemaComSmell {
    public void algumComportamento(Matematica m) {
        // ❌ recebe Matematica, mas pode vir uma NotaFiscal — semanticamente errado
        int resultado = m.quadrado(10,5);
        System.out.println("Resultado matemático: " + resultado);
    }
}


// ─── SOLUÇÃO: Composição ou uso direto de Matematica ─────────────────────────

/**
 * ✅ NotaFiscal não herda de Matematica — usa composição se precisar.
 *
 * Aniche, p.121:
 * "Lembre-se que quando a classe filho herda de uma classe pai qualquer,
 *  mas 'não quer' isso, você tem um mau cheiro no seu projeto de classes."
 */
class NotaFiscalCorreta {
    private final double valor;
    private final int qtd;
    private Matematica matematica = new Matematica(); // composição se precisar

    public NotaFiscalCorreta(double valor, int qtd) {
        this.valor = valor;
        this.qtd = qtd;
    }

    public double calculaImposto() {
        // ✅ usa Matematica via composição, não herança
        return matematica.quadrado((int) valor,qtd) * 0.1;
    }
}