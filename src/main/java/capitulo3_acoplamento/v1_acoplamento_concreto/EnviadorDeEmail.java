package capitulo3_acoplamento.v1_acoplamento_concreto;

/**
 * Uma ferramenta concreta: O Pombo-Correio específico.
 * Se o Bruxo depender diretamente desta classe, ele só saberá enviar mensagens
 * usando este método exato. Se precisar usar telepatia (SMS), terá problemas.
 *
 * @author Weriton L. Petreca
 */

public class EnviadorDeEmail {
    public void envia(NotaFiscal nf) {
        System.out.println("Enviando email com a NF...");
    }
}
