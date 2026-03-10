package capitulo7_interfaces_magras.v2_interfaces_coesas;

public class NotaFiscal {
    private String descricao;

    public NotaFiscal(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "NF[" + descricao + "]";
    }
}
