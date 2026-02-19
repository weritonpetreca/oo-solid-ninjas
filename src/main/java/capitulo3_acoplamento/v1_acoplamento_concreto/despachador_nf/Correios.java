package capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf;

/**
 * 🚚 O MENSAGEIRO (INFRAESTRUTURA)
 *
 * Classe concreta que realiza o envio físico.
 *
 * ⚠️ PROBLEMA DE ACOPLAMENTO:
 * O Despachador conhece os métodos específicos desta classe (Sedex10 vs SedexComum).
 * Se mudarmos o nome do método ou adicionarmos um novo tipo de envio,
 * teremos que alterar o Despachador.
 */
public class Correios {
    public void enviarPorSedex10(NotaFiscal nf) {
        System.out.println("Correios: Enviando por SEDEX 10 (Urgente)!");
    }

    public void enviarPorSedexComum(NotaFiscal nf) {
        System.out.println("Correios: Enviando por SEDEX Comum.");
    }
}
