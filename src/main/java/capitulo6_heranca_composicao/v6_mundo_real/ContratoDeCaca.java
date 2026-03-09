package capitulo6_heranca_composicao.v6_mundo_real;

/**
 * Representa um contrato de caça da Guilda.
 * Cada contrato tem um valor base e um multiplicador de dificuldade.
 */
public class ContratoDeCaca {
    private final String monstro;
    private final double valorBase;
    private final int nivelDePerigo; // 1 a 5

    public ContratoDeCaca(String monstro, double valorBase, int nivelDePerigo) {
        if (nivelDePerigo < 1 || nivelDePerigo > 5)
            throw new IllegalArgumentException("Nível de perigo deve ser entre 1 e 5");
        this.monstro = monstro;
        this.valorBase = valorBase;
        this.nivelDePerigo = nivelDePerigo;
    }

    public double getValorBase() { return valorBase; }
    public int getNivelDePerigo() { return nivelDePerigo; }
    public String getMonstro() { return monstro; }
}
