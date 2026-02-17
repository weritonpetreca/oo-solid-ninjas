package capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters;

/**
 * 🔧 ADAPTER: ENTREGADOR DE NOTAS FISCAIS
 *
 * Implementação concreta do contrato {@link Entregador}.
 * Encapsula a inteligência de decidir "como" entregar.
 *
 * ⚔️ EVOLUÇÃO DA V2:
 * Na v2, esta classe existia mas era concreta no construtor do Despachador.
 * Agora ela implementa a interface Entregador.
 * O Despachador passou a depender da abstração, não desta classe.
 *
 * 🛡️ ALTA COESÃO:
 * Toda a lógica de "como entregar uma NF" está aqui.
 * LeiDeEntrega e Correios são detalhes invisíveis ao mundo externo.
 *
 * 💡 POSSIBILIDADE REAL:
 * Amanhã podemos criar EntregadorPorDrone implements Entregador
 * e plugar no Despachador sem tocar em nenhuma outra classe.
 */

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports.Entregador;

public class EntregadorDeNFs implements Entregador {

    private final LeiDeEntrega lei;
    private final Correios correios;

    public EntregadorDeNFs(LeiDeEntrega lei, Correios correios){
        this.lei = lei;
        this.correios = correios;
    }

    @Override
    public void entrega(NotaFiscal nf) {
        if (lei.deveEntregarUrgente(nf)) {
            correios.enviarPorSedex10(nf);
        } else {
            correios.enviarPorSedexComum(nf);
        }
    }
}
