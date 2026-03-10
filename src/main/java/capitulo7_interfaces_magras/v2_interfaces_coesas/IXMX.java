package capitulo7_interfaces_magras.v2_interfaces_coesas;

/**
 * IXMX implementa SÓ CalculadorDeImposto.
 *
 * ✅ Não precisa fingir que gera nota
 * ✅ Sem exceção, sem null, sem gambiarra
 * ✅ O tipo deixa claro: IXMX só calcula imposto
 */
public class IXMX implements CalculadorDeImposto {
    @Override
    public double imposto(double valorCheio) {
        return 0.2 * valorCheio;
        // ✅ Sem geraNota() — sem mentira, sem exceção
    }
}
