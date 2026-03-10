package capitulo7_interfaces_magras.v4_repositorio_fabrica;

/**
 * Recebe suas dependências pelo construtor (DIP do Cap.3).
 * Alguém precisa instanciá-la — entra a Fábrica.
 */
public class CalculadoraDePrecos {
    private final TabelaDePreco tabela;
    private final ServicoDeEntrega entrega;

    public CalculadoraDePrecos(TabelaDePreco tabela, ServicoDeEntrega entrega) {
        this.tabela = tabela;
        this.entrega = entrega;
    }

    public double calcula(String destino) { return tabela.precoBase() + entrega.calculaFrete(destino); }
}
