package capitulo6_heranca_composicao.v1_lsp_violacao;

public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException() { super("Valor deve ser maior que zero"); }
}
