package capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters;

import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports.AcaoAposGerarNota;

/**
 * 🔧 ADAPTER: ESCRIBA SILENCIOSO (AUDITORIA)
 *
 * Implementação concreta do log de auditoria.
 *
 * ⚔️ NA VIDA REAL:
 * Aqui você poderia gravar em arquivo, enviar para Elasticsearch,
 * CloudWatch, Splunk, ou qualquer sistema de observabilidade.
 *
 * 🛡️ CROSS-CUTTING CONCERNS:
 * Log e auditoria são "preocupações transversais" — atravessam todo o sistema.
 * Com a arquitetura hexagonal, elas ficam isoladas em adapters específicos,
 * sem contaminar a lógica de negócio central.
 */

import java.time.LocalDateTime;

public class LogDeAuditoria implements AcaoAposGerarNota {
    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("📝 LogDeAuditoria: [" + LocalDateTime.now() + "] " +
                "NF gerada | Valor: " + nf.getValor() + " | Imposto: " + nf.getImposto());
    }
}
