package capitulo7_interfaces_magras.v4_repositorio_fabrica;

public class Fatura {
    private String descricao;
    private double valor;

    public Fatura(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Fatura[" + descricao + " R$" + valor + "]";
    }
}
