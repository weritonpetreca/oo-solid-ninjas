package capitulo7_interfaces_magras.v1_interface_gorda;

public class NotaFiscal {
    private String info;
    private String infoAdicional;

    public NotaFiscal(String info, String infoAdicional) {
        this.info = info;
        this.infoAdicional = infoAdicional;
    }

    @Override
    public String toString() {
        return "NF[" + info + "]";
    }
}
