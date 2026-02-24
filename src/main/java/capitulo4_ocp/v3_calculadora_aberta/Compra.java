package capitulo4_ocp.v3_calculadora_aberta;

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
