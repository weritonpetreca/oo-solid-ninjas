package capitulo7_interfaces_magras.v2_interfaces_coesas;

/**
 * ISS implementa as DUAS interfaces — calcula E gera nota.
 * ✅ Sem problema — ISS faz ambas as coisas.
 */
public class ISS implements CalculadorDeImposto, GeradorDeNota {
    @Override
    public double imposto(double valorCheio) {
        return 0.1 * valorCheio;
    }

    @Override
    public NotaFiscal geraNota() {
        return new NotaFiscal("ISS - Imposto Sobre Serviços = 10%");
    }
}
