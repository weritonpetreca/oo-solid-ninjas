package capitulo5_encapsulamento.v2_intimidade_inapropriada;
/**
 * SOLUÇÃO v2: NotaFiscal que encapsula sua própria regra fiscal.
 *
 * Tell, Don't Ask na prática (Aniche, Cap. 5, seção 5.4):
 *   ✅ O cliente simplesmente diz: "calcule o imposto"
 *   ✅ A regra vive onde deveria viver: dentro da própria NotaFiscal
 *
 * Teste do encapsulamento (seção 5.5):
 *   O quê? → calculaValorImposto() — o nome diz tudo
 *   Como?  → não sei, e não preciso saber. Encapsulado!
 *
 * Benefício real: a Receita Federal mudou a alíquota?
 * Você muda em UM lugar. O resto do sistema não percebe.
 */
public class NotaFiscalEncapsulada {

    private double valor;
    private double valorSemImposto;

    public NotaFiscalEncapsulada(double valor, double valorSemImposto) {
        this.valor = valor;
        this.valorSemImposto = valorSemImposto;
    }

    /**
     * ✅ A regra fiscal mora aqui. Só aqui.
     * O cliente não precisa saber de limites, alíquotas ou ifs.
     */
    public double calcularValorImposto() {
        if (valorSemImposto > 10000) {
            return 0.06 * valor;
        }
        return 0.12 * valor;
    }

    public double getValor() {
        return valor;
    }
    // getValorSemImposto() foi removido — não há motivo para expô-lo
}
