package capitulo7_interfaces_magras.v4_repositorio_fabrica;

public class Correios implements ServicoDeEntrega {
    @Override
    public double calculaFrete(String destino) {
        return 15.0;
    }
}
