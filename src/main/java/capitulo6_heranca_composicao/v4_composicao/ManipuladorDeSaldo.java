package capitulo6_heranca_composicao.v4_composicao;

/**
 * Responsável por manipular saldo — extração via composição.
 *
 * Aniche, Cap. 6, seção 6.5:
 * A manipulação de saldo é o comportamento que muda entre tipos de conta.
 * Ao extraí-la para uma classe própria, tanto ContaComum quanto
 * ContaDeEstudante podem usá-la sem herança entre elas.
 *
 * ✅ Encapsula bem o comportamento de saldo
 * ✅ Reutilizável por qualquer tipo de conta
 * ✅ Testável de forma isolada
 */
public class ManipuladorDeSaldo {
    private double saldo;

    public ManipuladorDeSaldo() { this.saldo = 0; }

    public void adiciona(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        this.saldo += valor;
    }

    public void retira(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        if (valor > saldo) throw new IllegalStateException("Saldo insuficiente");
        this.saldo -= valor;
    }

    public void aplicaJuros(double taxa) { this.saldo *= (1 + taxa); }

    public double getSaldo() { return saldo; }
}
