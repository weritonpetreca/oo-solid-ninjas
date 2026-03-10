package capitulo7_interfaces_magras.v3_tributavel;

import java.util.ArrayList;
import java.util.List;

/**
 * NotaFiscal complexa — muitos atributos, muitas dependências.
 *
 * Ao implementar Tributavel, ela expõe apenas o que o CalculadorDeImposto precisa.
 * O calculador não precisa conhecer cliente, endereço, pagamento — nada disso.
 */
public class NotaFiscal implements Tributavel {

    private String cliente;
    private List<Item> itens;
    private String enderecoEntrega;
    private double valorTotal;

    public NotaFiscal(String cliente, String enderecoEntrega) {
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
        this.itens = new ArrayList<>();
    }

    public void adicionaItem(Item item) {
        itens.add(item);
        valorTotal += item.getValor();
    }

    @Override
    public List<Item> itensASeremTributados() {
        return List.copyOf(itens); // expõe só o necessário
    }

    public String getCliente() {
        return cliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
