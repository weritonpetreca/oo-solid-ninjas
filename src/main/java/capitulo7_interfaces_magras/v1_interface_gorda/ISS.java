package capitulo7_interfaces_magras.v1_interface_gorda;

/** ISS: calcula imposto E gera nota fiscal. Implementa tudo sem problema. */
public class ISS implements  Imposto {
    @Override
    public NotaFiscal geraNota() {
        return new NotaFiscal("ISS - 10%", "Imposto Sobre Serviços");
    }

    @Override
    public double imposto(double valorCheio) {
        return 0.1 * valorCheio;
    }
}
