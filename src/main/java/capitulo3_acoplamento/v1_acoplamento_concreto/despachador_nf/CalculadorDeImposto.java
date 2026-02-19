package capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf;

/**
 * 💰 O COBRADOR DE IMPOSTOS
 *
 * Responsável pela lógica tributária.
 *
 * ⚠️ PROBLEMA DE ACOPLAMENTO:
 * O Despachador depende diretamente desta classe concreta.
 * Se quisermos trocar a regra de imposto (ex: ImpostoComplexo),
 * teremos que mudar o construtor do Despachador.
 */
public class CalculadorDeImposto {
    public double para(NotaFiscal nf) {
        System.out.println("CalculadorDeImposto: Calculando imposto...");
        return nf.getValor() * 0.06;
    }
}
