package capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters;

import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports.AcaoAposGerarNota;

/**
 * 🔧 ADAPTER: POMBO-CORREIO
 *
 * Implementação concreta da notificação por e-mail.
 *
 * ⚔️ NA VIDA REAL:
 * Aqui você usaria JavaMail, SendGrid, AWS SES, ou qualquer biblioteca de envio.
 * O use case (Gerador) nunca saberá qual biblioteca está por trás.
 *
 * 🛡️ TESTABILIDADE:
 * Nos testes do Gerador, mockamos a interface AcaoAposGerarNota.
 * Nunca precisamos mockar classes de infraestrutura como esta.
 */

public class EnviadorDeEmail implements AcaoAposGerarNota {
    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("📧 EnviadorDeEmail: Enviando email da NF " + nf.getValor() + "...");
    }
}
