package capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters;

/**
 * 🚚 O MENSAGEIRO (INFRAESTRUTURA)
 *
 * Implementação concreta do serviço de entrega física.
 * É um detalhe interno do {@link EntregadorDeNFs}.
 *
 * ⚔️ NA VIDA REAL:
 * Aqui poderia haver uma chamada HTTP para a API dos Correios,
 * ou para qualquer outro serviço de logística.
 * O Despachador nunca saberá disso.
 */

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;

public class Correios {

    public void enviarPorSedex10(NotaFiscal nf) {
        System.out.println("Correios: Enviando por SEDEX10 (Urgente)!");
    }

    public void enviarPorSedexComum(NotaFiscal nf) {
        System.out.println("Correios: Enviando por SEDEX Comum.");
    }
}
