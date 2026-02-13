package capitulo3_acoplamento.v2_inversao_dependencia;

/**
 * O Mestre Bruxo que domina o DIP (Princípio da Inversão de Dependência).
 *
 * A MÁGICA:
 * Ele não conhece "EnviadorDeEmail" nem "NotaFiscalDao".
 * Ele conhece apenas uma lista de "AcaoAposGerarNota".
 *
 * VANTAGENS:
 * 1. Flexibilidade: Podemos adicionar Logs, SMS, SAP, sem mudar esta classe.
 * 2. Testabilidade: Podemos passar uma lista com "Mocks" no teste unitário.
 *
 * @author Weriton L. Petreca
 */

import java.util.List;

public class GeradorDeNotaFiscal {

    private final List<AcaoAposGerarNota> acoes;

    // Injeção de Dependência da Abstração (Interface).
    // O Gerador aceita qualquer lista de aliados que sigam o contrato.
    public GeradorDeNotaFiscal(List<AcaoAposGerarNota> acoes) {
        this.acoes = acoes;
    }

    public NotaFiscal gera(double valor) {
        double imposto = valor * 0.06;
        NotaFiscal nf = new NotaFiscal(valor, imposto);

        // O Loop Polimórfico:
        // Executa todas as ações sem saber quais são.
        for (AcaoAposGerarNota acao : acoes) {
            acao.executa(nf);
        }

        return nf;
    }
}
