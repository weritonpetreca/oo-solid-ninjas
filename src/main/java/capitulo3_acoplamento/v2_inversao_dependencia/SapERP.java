package capitulo3_acoplamento.v2_inversao_dependencia;

/**
 * Um novo aliado (Sistema Externo/SAP).
 * Graças ao DIP, podemos adicionar este novo comportamento sem tocar
 * no código do GeradorDeNotaFiscal. O código está "Aberto para Extensão".
 *
 * @author Weriton L. Petreca
 */

public class SapERP implements AcaoAposGerarNota {
    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("Enviando NF para o SAP (via interface)...");
    }
}
