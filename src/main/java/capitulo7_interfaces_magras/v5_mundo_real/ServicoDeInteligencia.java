package capitulo7_interfaces_magras.v5_mundo_real;

import java.util.List;

/**
 * Serviço de Inteligência da Guilda — distribui comunicados pelo Continente.
 *
 * Aplica ISP + DIP do livro:
 *   ✅ Recebe List<MeioDeComunicacao> — plugável via construtor (DIP)
 *   ✅ Recebe Destinatario — não sabe se é Cacador, Guilda ou Rei
 *   ✅ Novo meio (Sonho de Bruxa, Magia de Triss)? Zero mudança aqui
 *   ✅ Interfaces magras facilitam dubles de teste
 */
public class ServicoDeInteligencia {

    private final List<MeioDeComunicacao> meios;

    public ServicoDeInteligencia(List<MeioDeComunicacao> meios) { this.meios = meios; }

    public void comunicar(Destinatario destinatario, String mensagem) {
        for (MeioDeComunicacao meio : meios) {
            meio.envia(destinatario, mensagem);
        }
    }

    public void alertaTodos(List<Destinatario> destinatarios, String mensagem) {
        for (Destinatario dest : destinatarios) {
            comunicar(dest, mensagem);
        }
    }
}
