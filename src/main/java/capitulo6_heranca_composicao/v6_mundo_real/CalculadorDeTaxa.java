package capitulo6_heranca_composicao.v6_mundo_real;

/**
 * Composição: estratégia de cálculo de taxa da Guilda.
 * Cada escola negocia taxas diferentes com a Guilda do Continente.
 */
public interface CalculadorDeTaxa {
    double calculaTaxa(double recompensa);

}
