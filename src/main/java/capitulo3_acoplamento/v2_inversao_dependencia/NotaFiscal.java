package capitulo3_acoplamento.v2_inversao_dependencia;

/**
 * O mesmo Troféu (Domínio), mas agora utilizado num contexto desacoplado.
 *
 * DIFERENÇA PARA V1:
 * Aqui, esta classe atua como o elo de ligação (Acoplamento Estável).
 * O Gerador não conhece o DAO, e o DAO não conhece o Gerador,
 * mas AMBOS conhecem a NotaFiscal.
 *
 * Ela é a estrutura de dados estável que permite a comunicação através da Interface.
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
