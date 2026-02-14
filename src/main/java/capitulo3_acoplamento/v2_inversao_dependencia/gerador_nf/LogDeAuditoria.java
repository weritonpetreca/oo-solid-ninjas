package capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf;

import java.time.LocalDateTime;

/**
 * O Escriba Silencioso (Auditoria).
 * Esta classe prova o poder do Princípio Aberto/Fechado (OCP).
 * Nós a adicionamos ao "bando" sem que o Líder (GeradorDeNotaFiscal)
 * precisasse saber de sua existência.
 * Função: Registrar no tecido do tempo (Console) o momento exato
 * em que a missão foi cumprida.
 *
 * @author Weriton L. Petreca
 */


public class LogDeAuditoria implements AcaoAposGerarNota {

    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("AUDITORIA: O contrato foi selado em : " + LocalDateTime.now()
                + " | Valor: " + nf.getValor());
    }
}
