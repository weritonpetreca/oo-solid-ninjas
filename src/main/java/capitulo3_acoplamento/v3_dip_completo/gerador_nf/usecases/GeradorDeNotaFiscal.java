package capitulo3_acoplamento.v3_dip_completo.gerador_nf.usecases;

import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.Fatura;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports.AcaoAposGerarNota;

import java.util.List;

/**
 * 🐺 O GERADOR MESTRE (USE CASE)
 *
 * Esta classe representa o caso de uso central: "Gerar uma Nota Fiscal".
 *
 * ⚔️ COMPARE COM AS VERSÕES ANTERIORES:
 *
 * V1 — Conhecia: EnviadorDeEmail, NotaFiscalDao (concretos)
 * V2 — Conhecia: List<AcaoAposGerarNota> (interface, mas sem separação física)
 * V3 — Conhece: List<AcaoAposGerarNota> (interface + arquitetura hexagonal)
 *
 * 🛡️ IMPORTS RESTRITOS:
 * Olhe os imports desta classe. Ela só importa:
 * - capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.*
 * - capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports.*
 *
 * Jamais importa algo de adapters/.
 * Essa é a garantia arquitetural do Hexagonal.
 *
 * 🧪 TESTABILIDADE PERFEITA:
 * Para testar esta classe, mockamos apenas a interface AcaoAposGerarNota.
 * Não precisamos de banco, e-mail, SAP, nada de infraestrutura.
 *
 * 💡 OBSERVER PATTERN:
 * Esta classe é o "Subject" do padrão Observer.
 * Ela mantém uma lista de observadores e os notifica quando o evento acontece.
 */

public class GeradorDeNotaFiscal {

    private final List<AcaoAposGerarNota> acoes;

    public GeradorDeNotaFiscal(List<AcaoAposGerarNota> acoes) {
        this.acoes = acoes;
    }

    public NotaFiscal gera(Fatura fatura) {
        double valor = fatura.getValorMensal();
        double imposto = impostoSimplesSobreO(valor);

        NotaFiscal nf = new NotaFiscal(valor, imposto);

        // Notifica todos os observadores
        for (AcaoAposGerarNota acao : acoes) {
            acao.executa(nf);
        }

        return nf;
    }

    private double impostoSimplesSobreO(double valor) {
        return valor * 0.06;
    }
}
