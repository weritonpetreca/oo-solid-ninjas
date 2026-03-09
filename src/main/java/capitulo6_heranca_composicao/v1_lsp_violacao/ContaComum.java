package capitulo6_heranca_composicao.v1_lsp_violacao;

/**
 * Conta bancária comum — define o CONTRATO da hierarquia.
 *
 * Contrato implícito de rende() (Aniche, Cap. 6):
 *   Pré-condição:  nenhuma
 *   Pós-condição:  executa sem lançar exceção; saldo aumenta 10%
 *
 * Qualquer código que usa ContaComum vai confiar nesse contrato.
 * Subclasses que quebrarem esse contrato violam o LSP.
 */
public class ContaComum {
    protected double saldo;

    public ContaComum() {
        this.saldo = 0;
    }

    public void deposita(double valor) {
        if (valor <= 0) throw new ValorInvalidoException();
        this.saldo += valor;
    }

    public void rende() { this.saldo *= 1.1; }

    public double getSaldo() { return saldo; }
}
