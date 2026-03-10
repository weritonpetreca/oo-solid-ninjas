package capitulo7_interfaces_magras.v5_mundo_real;

/**
 * Interface magra para meio de envio de mensagens no Continente.
 *
 * Aplica ISP do Cap. 7:
 *   ✅ Uma responsabilidade: enviar uma mensagem para um destinatário
 *   ✅ Corvo mensageiro, Pergaminho mágico, Cristal de comunicação — todos implementam
 *   ✅ O ServicoDeInteligencia não sabe como cada meio funciona
 */
@FunctionalInterface
public interface MeioDeComunicacao {
    void envia(Destinatario destinatario, String mensagem);
}
