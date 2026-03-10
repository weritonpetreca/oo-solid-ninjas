package capitulo7_interfaces_magras.v5_mundo_real;

/**
 * Meio tradicional: corvo mensageiro. Lento mas confiável.
 * Limitação: mensagem curta (pergaminhos são caros no Continente).
 */
public class CorvoMensageiro implements MeioDeComunicacao{
    @Override
    public void envia(Destinatario destinatario, String mensagem) {
        String msgCurta = mensagem.length() > 80
                ? mensagem.substring(0, 77) + "..."
                : mensagem;
        System.out.printf("[CORVO] -> %s em %s%n \"%s\"%n",
                destinatario.nomeCompleto(), destinatario.identificacao(), msgCurta);
    }
}
