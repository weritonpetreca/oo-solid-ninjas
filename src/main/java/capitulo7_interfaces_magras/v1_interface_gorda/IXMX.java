package capitulo7_interfaces_magras.v1_interface_gorda;

/**
 * PROBLEMA v1: IXMX não gera nota fiscal, mas a interface obriga.
 *
 * Aniche, Cap. 7:
 * O desenvolvedor tem duas saídas ruins:
 *   1. Lançar exceção → quebra LSP (caller não espera isso)
 *   2. Retornar null → NullPointerException esperando acontecer
 *
 * Isso é o preço de uma interface gorda.
 */
public class IXMX implements Imposto {
    @Override
    public NotaFiscal geraNota() {
        // ❌ Opção 1: lançar exceção — quebra contrato da interface
        throw new NaoGeraNotaException();
        // ❌ Opção 2: return null; — NullPointerException esperando
        // return null;
    }

    @Override
    public double imposto(double valorCheio) {
        return 0.2 * valorCheio;
    }
}
