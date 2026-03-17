package capitulo9_maus_cheiros.v2_feature_envy;

/**
 * ════════════════════════════════════════════════════════════════
 * MAUS CHEIROS — Feature Envy
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 9, seção 9.2:
 * "Feature Envy é o nome que damos para quando um método está mais
 *  interessado em outro objeto do que no objeto em que ele está inserido."
 *
 * "Inveja de Feature" — o método inveja a classe vizinha e quer
 * manipular tudo que é dela.
 *
 * Sinal clássico: o método só usa dados de outro objeto.
 * O comportamento está no lugar errado.
 */

// ─── PROBLEMA ────────────────────────────────────────────────────────────────

class ContratoDeCacaComSmell {
    private final String monstro;
    private final double valorBase;
    private final int nivelDePerigo;
    private boolean encerrado;
    private double impostoCalculado;

    ContratoDeCacaComSmell(String monstro, double valorBase, int nivelDePerigo) {
        this.monstro       = monstro;
        this.valorBase     = valorBase;
        this.nivelDePerigo = nivelDePerigo;
    }

    public String getMonstro()      { return monstro; }
    public double getValorBase()    { return valorBase; }
    public int getNivelDePerigo()   { return nivelDePerigo; }
    public boolean isEncerrado()    { return encerrado; }
    public void setEncerrado(boolean e) { this.encerrado = e; }
    public void setImposto(double i)    { this.impostoCalculado = i; }
    public double getImposto()          { return impostoCalculado; }
}

/**
 * ❌ ProcessadorDeContratos tem FEATURE ENVY de ContratoDeCaca.
 *    O método processarContrato() não usa NADA da própria classe.
 *    Toda a lógica manipula dados de ContratoDeCaca.
 *    Esse comportamento deveria estar DENTRO de ContratoDeCaca.
 */
class ProcessadorDeContratosComSmell {

    public void processarContrato(ContratoDeCacaComSmell contrato) {
        // ❌ Só usa dados de 'contrato' — Feature Envy total
        double imposto = contrato.getValorBase() * 0.15;

        if (contrato.getNivelDePerigo() > 3) {
            imposto = imposto * 1.5; // missões perigosas pagam mais imposto
        }

        contrato.setImposto(imposto);
        contrato.setEncerrado(true);

        System.out.printf("Contrato '%s' processado. Imposto: R$%.2f%n",
                contrato.getMonstro(), imposto);
    }
}

// ─── SOLUÇÃO ─────────────────────────────────────────────────────────────────

/**
 * ✅ ContratoDeCaca com o comportamento onde ele pertence.
 *    Mover a lógica para dentro da classe elimina o Feature Envy.
 *    O processador chama um único método — sem conhecer os detalhes.
 */
class ContratoDeCaca {
    private final String monstro;
    private final double valorBase;
    private final int nivelDePerigo;
    private boolean encerrado;
    private double impostoCalculado;

    ContratoDeCaca(String monstro, double valorBase, int nivelDePerigo) {
        this.monstro       = monstro;
        this.valorBase     = valorBase;
        this.nivelDePerigo = nivelDePerigo;
    }

    /**
     * ✅ Regra encapsulada aqui — onde os dados vivem.
     *    Ninguém de fora precisa saber como o imposto é calculado.
     */
    public void processar() {
        this.impostoCalculado = valorBase * 0.15;
        if (nivelDePerigo > 3) {
            this.impostoCalculado *= 1.5;
        }
        this.encerrado = true;
        System.out.printf("Contrato '%s' processado. Imposto: R$%.2f%n",
                monstro, impostoCalculado);
    }

    public double getImposto()   { return impostoCalculado; }
    public boolean isEncerrado() { return encerrado; }
    public String getMonstro()   { return monstro; }
}

/**
 * ✅ ProcessadorDeContratos agora só orquestra — sem Feature Envy.
 *    Ele pode ter suas próprias responsabilidades legítimas.
 */
class ProcessadorDeContratosLimpo {

    public void processar(ContratoDeCaca contrato) {
        // ✅ Delega — o contrato sabe processar a si mesmo
        contrato.processar();
        // Responsabilidade legítima do processador: logar, notificar, persistir...
        System.out.println("    [LOG] Contrato finalizado pelo processador.");
    }
}