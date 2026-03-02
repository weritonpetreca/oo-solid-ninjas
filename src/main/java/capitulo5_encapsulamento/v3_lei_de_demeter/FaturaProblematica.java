package capitulo5_encapsulamento.v3_lei_de_demeter;

/**
 * PROBLEMA v3: Fatura que expõe seu Cliente diretamente.
 *
 * Ao oferecer getCliente(), a Fatura permite que qualquer um
 * acesse o Cliente e o manipule diretamente.
 *
 * Isso cria acoplamento INDIRETO: classes que usam Fatura
 * passam a depender também de Cliente — sem declarar isso explicitamente.
 */
public class FaturaProblematica {

    private double valor;
    private Cliente cliente;

    public FaturaProblematica(double valor, Cliente cliente) {
        this.valor = valor;
        this.cliente = cliente;
    }

    public double getValor() {
        return valor;
    }

    // ❌ Esse getter permite a cadeia: fatura.getCliente().marcaComoInadimplente()
    public Cliente getCliente() {
        return cliente;
    }
}
