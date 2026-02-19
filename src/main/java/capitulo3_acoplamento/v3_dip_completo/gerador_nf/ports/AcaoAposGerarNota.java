package capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports;

import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.NotaFiscal;

/**
 * 📜 PORTA: OBSERVADORES DO CONTRATO
 *
 * Contrato sagrado para qualquer ação que deva ser executada
 * após a geração de uma Nota Fiscal.
 *
 * ⚔️ DESIGN PATTERN: OBSERVER
 * Esta interface implementa o padrão Observer (Observador).
 * O Gerador é o Subject (Sujeito) que notifica os Observers.
 *
 * 🛡️ DIP + OCP NA PRÁTICA:
 * - DIP: O use case (Gerador) depende desta abstração, não de implementações.
 * - OCP: Para adicionar uma nova ação (ex: EnviarSlack), criamos um novo
 *   adapter que implementa esta interface. O Gerador não muda uma vírgula.
 *
 * 💡 FLEXIBILIDADE:
 * - Hoje: Email, DAO, SAP, SMS, Log
 * - Amanhã: Slack, Webhook, Kafka, SQS...
 * Todos implementam este mesmo contrato.
 */

public interface AcaoAposGerarNota {
    void executa(NotaFiscal nf);
}
