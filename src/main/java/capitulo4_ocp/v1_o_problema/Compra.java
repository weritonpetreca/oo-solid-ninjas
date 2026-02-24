package capitulo4_ocp.v1_o_problema;
/**
 * 🛍️ COMPRA (OBJETO DE DOMÍNIO)
 *
 * Representa o pedido do cliente: um produto com valor e destino.
 * É o input da nossa {@link CalculadoraDePrecos}.
 *
 * ⚔️ NOTA DE DOMÍNIO:
 * Ela é uma classe estável. Depender dela não é um problema.
 * O que queremos evitar é depender das classes de SERVIÇO de forma rígida.
 */

public class Compra {

    private final String produto;
    private final double valor;
    private final String cidade;

    public Compra(String produto, double valor, String cidade) {
        this.produto = produto;
        this.valor = valor;
        this.cidade = cidade;
    }

    public String getProduto() {
        return produto;
    }

    public double getValor() {
        return valor;
    }

    public String getCidade() {
        return cidade;
    }
}
