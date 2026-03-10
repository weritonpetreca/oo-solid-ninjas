package capitulo7_interfaces_magras.v3_tributavel;

/**
 * SOLUÇÃO v3: CalculadorDeImposto recebe Tributavel, não NotaFiscal.
 *
 * Aniche, Cap. 7, seção 7.2:
 * Vantagens:
 *   ✅ Menos acoplado: Tributavel é mais estável que NotaFiscal
 *   ✅ Mais semântico: "algo tributável", não "qualquer coisa"
 *   ✅ Mais reutilizável: funciona com NF, pedido, contrato — qualquer Tributavel
 *   ✅ Mais testável: fácil criar um Tributavel de mentira no teste
 */
public class CalculadorDeImposto {

    public double calcula(Tributavel t) {
        double total = 0;
        for (Item item : t.itensASeremTributados()) {
            if (item.getValor() > 1000.0) {
                total += item.getValor() * 0.02;
            } else {
                total += item.getValor() * 0.01;
            }
        }
        return total;
    }
}
