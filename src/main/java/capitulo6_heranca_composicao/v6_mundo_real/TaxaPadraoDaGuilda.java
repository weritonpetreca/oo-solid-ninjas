package capitulo6_heranca_composicao.v6_mundo_real;

/** Taxa padrão: 15% para a Guilda do Continente. */
public class TaxaPadraoDaGuilda implements CalculadorDeTaxa {
    @Override
    public double calculaTaxa(double recompensa) {
        return recompensa * 0.15;
    }
}
