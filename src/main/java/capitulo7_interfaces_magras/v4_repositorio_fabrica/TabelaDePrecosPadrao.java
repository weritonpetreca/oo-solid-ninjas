package capitulo7_interfaces_magras.v4_repositorio_fabrica;

public class TabelaDePrecosPadrao implements TabelaDePreco {
    @Override
    public double precoBase() {
        return 100.0;
    }
}
