package capitulo8_consistencia.v1_construtor_rico;

/**
 * Nível de perigo de uma missão no Continente.
 * Cada nível tem um multiplicador para a taxa da Guilda.
 */
public enum NivelDePerigo {
    BAIXO(0.10),
    MEDIO(0.15),
    ALTO(0.20),
    EXTREMO(0.30);

    private final double multiplicadorDeTaxa;

    NivelDePerigo(double multiplicadorDeTaxa) { this.multiplicadorDeTaxa = multiplicadorDeTaxa; }

    public double getMultiplicadorDeTaxa() { return multiplicadorDeTaxa; }
}
