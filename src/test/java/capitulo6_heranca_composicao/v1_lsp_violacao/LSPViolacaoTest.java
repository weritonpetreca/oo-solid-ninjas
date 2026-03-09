package capitulo6_heranca_composicao.v1_lsp_violacao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("🚨 v1: A Maldição da Herança (Violação do LSP)")
class LSPViolacaoTest {

    @Test
    @DisplayName("Conta Comum deve render normalmente (Comportamento Base)")
    void contaComumDeveRenderSemExcecao() {
        ContaComum conta = new ContaComum();
        conta.deposita(1000.0);
        conta.rende();
        assertEquals(1100.0, conta.getSaldo(), 0.001);
    }

    @Test
    @DisplayName("Conta de Estudante quebra o contrato ao lançar exceção (Violação LSP)")
    void contaDeEstudanteDeveQuebrarAoRender() {
        ContaDeEstudante conta = new ContaDeEstudante();
        conta.deposita(1000.0);

        // ❌ O que o LSP proíbe: subclasse lança exceção onde pai não lançava
        assertThrows(ContaNaoRendeException.class, conta::rende);
    }

    @Test
    @DisplayName("Processador explode ao tratar subclasse como se fosse a classe pai")
    void processarExplodeComContaDeEstudanteNaLista() {
        ContaComum contaNormal = new ContaComum();
        contaNormal.deposita(500.0);

        ContaDeEstudante contaEstudante = new ContaDeEstudante();
        contaEstudante.deposita(200.0);

        // Lista polimórfica — o compilador aceita sem reclamar
        List<ContaComum> contas = List.of(contaNormal, contaEstudante);
        ProcessadorDeInvestimentos processador = new ProcessadorDeInvestimentos();

        // ❌ Explode em runtime ao chegar na ContaDeEstudante
        // Isso é o LSP quebrado na prática
        assertThrows(ContaNaoRendeException.class,
                () -> processador.processaRendimento(contas));
    }
}
