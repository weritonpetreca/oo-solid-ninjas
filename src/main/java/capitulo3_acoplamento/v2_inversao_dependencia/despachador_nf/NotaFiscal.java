package capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf;

/**
 * 📜 O TROFÉU (DOMÍNIO)
 *
 * Objeto de dados que trafega entre as camadas.
 * É o elo comum entre todas as classes deste pacote.
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
