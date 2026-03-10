package capitulo7_interfaces_magras.v1_interface_gorda;

/**
 * PROBLEMA v1: Interface "gorda" (fat interface).
 *
 * Aniche, Cap. 7:
 * Essa interface tem DUAS responsabilidades distintas:
 *   1. Calcular o valor do imposto
 *   2. Gerar uma nota fiscal
 *
 * Nem todo imposto emite nota fiscal.
 * Então qualquer implementação que não gera nota é forçada a mentir:
 * lançar exceção ou retornar null — ambos quebram o LSP.
 *
 * Interface gorda → baixo reúso + gambiarras inevitáveis.
 */
public interface Imposto {
    NotaFiscal geraNota();              // Responsabilidade 1: Gerar Nota
    double imposto(double valorCheio);  // Responsabilidade 2: Calcular Imposto
}
