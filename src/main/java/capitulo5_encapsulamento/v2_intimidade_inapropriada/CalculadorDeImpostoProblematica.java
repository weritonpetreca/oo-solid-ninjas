package capitulo5_encapsulamento.v2_intimidade_inapropriada;
/**
 * PROBLEMA v2: Classe externa que "sabe demais" sobre NotaFiscal.
 *
 * Viola Tell, Don't Ask (Aniche, Cap. 5, seção 5.4):
 *
 *   ❌ ASK: pergunta getValorSemImposto()
 *   ❌ DECIDE: o if com a regra fiscal fica aqui fora
 *   ❌ AGE: calcula o valor manualmente
 *
 * O que acontece quando a Receita Federal muda a alíquota?
 * Você vai procurar quantos lugares têm esse if? CTRL+F não é arquitetura.
 */
public class CalculadorDeImpostoProblematica {

    public double calcula(NotaFiscal nf) {
        double valor;

        // ❌ INTIMIDADE INAPROPRIADA: essa classe conhece a regra interna da NotaFiscal
        // ❌ TELL DON'T ASK: primeiro perguntamos, depois decidimos
        if (nf.getValorSemImposto() > 1000) {
            valor = 0.06 * nf.getValor();
        } else {
            valor = 0.12 * nf.getValor();
        }

        return valor;
    }
}
