package capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf;

/**
 * 💰 O COBRADOR DE IMPOSTOS
 *
 * Responsável pela lógica tributária.
 * Mantém o Despachador coeso, tirando dele a responsabilidade de saber a alíquota.
 */
public class CalculadorDeImposto {

    public double para(NotaFiscal nf) {
        System.out.println("CalculadorDeImposto: Calculando imposto...");
        return nf.getValor() * 0.06;
    }
}
