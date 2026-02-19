package capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf;

/**
 * 🚚 O MENSAGEIRO (INFRAESTRUTURA)
 *
 * Classe concreta que realiza o envio físico.
 * Na arquitetura ideal (DIP), esta classe deveria implementar uma interface "Enviador".
 * Mas neste passo da refatoração, estamos focando apenas em AGRUPAR responsabilidades.
 */
public class Correios {
    public void enviarPorSedex10(NotaFiscal nf) {
        System.out.println("Correios: Enviando nota fiscal por Sedex 10...");
    }

    public void enviarPorSedexComum(NotaFiscal nf) {
        System.out.println("Correios: Enviando nota fiscal por Sedex comum...");
    }
}
