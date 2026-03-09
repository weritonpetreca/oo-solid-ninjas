package capitulo6_heranca_composicao.v1_lsp_violacao;

import java.util.List;

/**
 * Usa polimorfismo para processar rendimentos de todas as contas.
 *
 * Escrito assumindo o contrato de ContaComum: rende() funciona sem exceção.
 * Se uma ContaDeEstudante entrar na lista → explosão em runtime.
 *
 * Ninguém vê isso no código daqui. Nenhum compilador avisa.
 * É exatamente por isso que o LSP existe.
 */
public class ProcessadorDeInvestimentos {

    public void processaRendimento(List<ContaComum> contasDoBanco){

        for (ContaComum conta : contasDoBanco) {
            conta.rende(); // ❌ ContaDeEstudante vai lançar exceção aqui

            System.out.println("Novo Saldo:");
            System.out.println(conta.getSaldo());
        }
    }
}
