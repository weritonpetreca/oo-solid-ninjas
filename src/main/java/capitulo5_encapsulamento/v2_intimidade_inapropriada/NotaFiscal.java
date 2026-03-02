package capitulo5_encapsulamento.v2_intimidade_inapropriada;
/**
 * PROBLEMA v2: NotaFiscal que expõe seus internals.
 *
 * Quem usa essa classe precisa saber:
 *   - que existe getValorSemImposto()
 *   - que o limite é 10000
 *   - que abaixo aplica 12%, acima aplica 6%
 *
 * Isso é "intimidade inapropriada" (Aniche, Cap. 5, seção 5.2):
 * a classe cliente sabe mais do que deveria sobre como NotaFiscal funciona.
 *
 * Se a regra fiscal mudar (e SEMPRE muda), você vai caçar esse if
 * espalhado por todo o sistema.
 */
public class NotaFiscal {

    private double valor;
    private double valorSemImposto;

    public NotaFiscal(double valor, double valorSemImposto) {
        this.valor = valor;
        this.valorSemImposto = valorSemImposto;
    }

    public double getValor() {
        return valor;
    }

    // ❌ Esse getter força o cliente a conhecer a regra fiscal
    public double getValorSemImposto() {
        return valorSemImposto;
    }
}
