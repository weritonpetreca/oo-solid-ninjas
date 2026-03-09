package capitulo6_heranca_composicao.v1_lsp_violacao;

/**
 * PROBLEMA v1: ContaDeEstudante quebra o LSP (Aniche, Cap. 6, seção 6.1).
 *
 * Do ponto de vista do mundo real, faz sentido: conta de estudante não rende.
 * Do ponto de vista do LSP: é uma catástrofe silenciosa.
 *
 * O contrato de ContaComum diz: rende() não lança exceção.
 * ContaDeEstudante diz: rende() LANÇA ContaNaoRendeException.
 *
 * ❌ Pós-condição afrouxada → LSP violado.
 *
 * Consequência real:
 *   Todo código que faz List<ContaComum> e chama conta.rende()
 *   pode explodir em runtime — sem aviso do compilador.
 *   O ProcessadorDeInvestimentos é um exemplo vivo disso.
 */
public class ContaDeEstudante extends ContaComum {

    public void rende() {
        // ❌ Promessa do pai: não lança exceção
        // ❌ Filho: lança exceção → contrato quebrado → LSP violado
        throw new ContaNaoRendeException();
    }
}
