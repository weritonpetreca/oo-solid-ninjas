package capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf;

/**
 * O Mensageiro Telepático (Recurso Caro).
 * Diferente dos outros aliados, este possui uma vontade própria baseada em custos.
 * O uso de Telepatia (SMS) consome muita mana (dinheiro).
 *
 * Lição de Arquitetura:
 * A regra de "quando enviar" está encapsulada AQUI.
 * O Gerador não sabe que existe um limite mínimo. Ele apenas invoca.
 * O objeto decide se age ou se ignora. Isso mantém a inteligência distribuída
 * e o Gerador focado apenas na orquestração.
 *
 * @author Weriton L. Petreca
 */
public class EnviadorDeSMS implements AcaoAposGerarNota {

    @Override
    public void executa(NotaFiscal nf) {
        // O Filtro Mágico: Só gastamos mana se a recompensa for alta.
        if (nf.getValor() >= 1000) {
            System.out.println("SMS (Telepatia): Mensagem enviada! O contrato é valioso.");
        } else {
            // Opcional: Logar que foi ignorado, ou apenas silêncio.
            System.out.println("SMS (Telepatia): Valor baixo demais. Mana economizada.");
        }
    }
}
