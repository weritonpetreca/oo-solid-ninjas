package capitulo3_acoplamento.v1_acoplamento_concreto;

/**
 * Representa o contrato cumprido, o troféu da caçada.
 * É uma classe simples de dados (Dominio), usada para transportar as informações.
 *
 * @author Weriton L. Petreca
 */

public class NotaFiscal {

    private double valor;
    private double imposto;

    public NotaFiscal(double valor, double imposto) {
        this.valor = valor;
        this.imposto = imposto;
    }

    public double getValor() {
        return valor;
    }

    public double getImposto() {
        return imposto;
    }
}
