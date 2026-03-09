package capitulo6_heranca_composicao.v6_mundo_real;

/** Escola do Lobo tem acordo especial: apenas 10% de taxa. */
public class TaxaNegociadaEscolaDoLobo implements CalculadorDeTaxa {
    @Override
    public double calculaTaxa(double recompensa) {
        return recompensa * 0.10;
    }
}
