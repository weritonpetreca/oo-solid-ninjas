package capitulo7_interfaces_magras.v5_mundo_real;

/**
 * Cristal de comunicação: transmite voz e imagem.
 * Exclusivo para caçadores de alto nível e membros da Conclave.
 */
public class CristalDeComunicacao implements MeioDeComunicacao {

    private final String frequencia;

    public CristalDeComunicacao(String frequencia) { this.frequencia = frequencia; }

    @Override
    public void envia(Destinatario destinatario, String mensagem) {
        System.out.printf("[CRISTAL/%s] 🔮 Transmitindo para %s: %s%n",
                frequencia, destinatario.identificacao(), mensagem);
    }
}
