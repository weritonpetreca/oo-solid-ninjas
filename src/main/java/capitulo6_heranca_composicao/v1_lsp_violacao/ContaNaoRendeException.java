package capitulo6_heranca_composicao.v1_lsp_violacao;

public class ContaNaoRendeException extends RuntimeException {
    public ContaNaoRendeException() { super("Conta de estudante não rende. "); }
}
