package capitulo7_interfaces_magras.v5_mundo_real;

/**
 * Pergaminho encantado por magos: entrega instantânea, sem limite de tamanho.
 * Caro — reservado para mensagens urgentes da Guilda.
 */
public class PergaminhoMagico implements MeioDeComunicacao {
    @Override
    public void envia(Destinatario destinatario, String mensagem) {
        System.out.printf("[PERGAMINHO MÁGICO] ✦ Para %s%n Mensagem completa: %s%n",
                destinatario.nomeCompleto(), mensagem);
    }
}
