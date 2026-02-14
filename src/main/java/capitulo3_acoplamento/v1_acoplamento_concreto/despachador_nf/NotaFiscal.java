package capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf;

/**
 * 📜 O TROFÉU (DOMÍNIO)
 *
 * Objeto de dados que trafega entre as camadas.
 * É o elo comum entre todas as classes deste pacote.
 *
 * 🛡️ NOTA DO BRUXO:
 * Depender de classes de domínio simples (POJOs) não é um problema grave.
 * O problema é depender de classes de SERVIÇO (como Correios, Dao, etc).
 */
public class NotaFiscal {
    private double valor;
    private double imposto;

    public NotaFiscal(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public double getImposto() {
        return imposto;
    }
}
