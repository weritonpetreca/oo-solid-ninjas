package capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters;

/**
 * 📜 A REGRA (DETALHE DE IMPLEMENTAÇÃO)
 *
 * Define a política de urgência para entregas.
 * É um detalhe interno do {@link EntregadorDeNFs} — o mundo exterior
 * não sabe que ela existe.
 *
 * ⚔️ ENCAPSULAMENTO:
 * O Despachador não conhece esta classe.
 * Apenas o EntregadorDeNFs sabe que ela existe.
 */

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;

public class LeiDeEntrega {

    public boolean deveEntregarUrgente(NotaFiscal nf) {
        System.out.println("LeidDeEntrega: Verificando urgência...");
        return nf.getValor() > 1000.0;
    }
}
