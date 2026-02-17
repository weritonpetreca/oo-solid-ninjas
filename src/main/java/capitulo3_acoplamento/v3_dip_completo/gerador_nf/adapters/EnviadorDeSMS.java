package capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters;

import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports.AcaoAposGerarNota;

/**
 * 🔧 ADAPTER: TELEPATIA (SMS)
 *
 * Implementação concreta da notificação por SMS.
 * Possui lógica de negócio interna: só envia se o valor for relevante.
 *
 * ⚔️ LIÇÃO DE DESIGN:
 * A decisão "Quando enviar?" está encapsulada AQUI, não no Gerador.
 * O Gerador apenas notifica todos os observadores. Cada um decide se age ou não.
 * Isso mantém o Gerador limpo e as regras distribuídas nos lugares certos.
 */

public class EnviadorDeSMS implements AcaoAposGerarNota {

    private static final double VALOR_MINIMO = 1000.0;

    @Override
    public void executa(NotaFiscal nf) {
        if (nf.getValor() >= VALOR_MINIMO) {
            System.out.println("📱 EnviadorDeSMS: SMS enviado! Valor alto: " + nf.getValor());
        } else {
            System.out.println("📱 EnviadorDeSMS: Valor baixo (" + nf.getValor() + "), SMS não enviado.");
        }
    }
}
