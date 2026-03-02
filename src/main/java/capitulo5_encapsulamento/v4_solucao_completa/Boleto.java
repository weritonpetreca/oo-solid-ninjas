package capitulo5_encapsulamento.v4_solucao_completa;

public class Boleto {

    private double valor;
    private String codigo;

    public Boleto(String codigo, double valor) {
        this.codigo = codigo;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public String getCodigo() {
        return codigo;
    }
}
