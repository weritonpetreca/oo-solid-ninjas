package capitulo3_acoplamento.v2_inversao_dependencia;

public class EnviadorDeEmail implements AcaoAposGerarNota {

    /**
     * O Pombo-Correio agora assina o contrato.
     * Ele é apenas uma das muitas formas de executar uma ação após gerar a nota.
     *
     * @author Weriton L. Petreca
     */

    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("Enviando email (via interface)...");
    }
}
