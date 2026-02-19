package capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports;

/**
 * 📜 PORTA: O MENSAGEIRO
 *
 * Contrato sagrado para qualquer forma de entrega.
 *
 * ⚔️ EVOLUÇÃO DA V2:
 * Na v2, EntregadorDeNFs era uma classe concreta no construtor do Despachador.
 * O Despachador sabia que existia um "EntregadorDeNFs" específico.
 *
 * Agora ele só sabe que existe "alguém que entrega".
 * Poderia ser Correios, Drone, Portador ou uma Pomba-Correio.
 * O Despachador não se importa. Ele apenas diz: "Entregue isso."
 *
 * 🧙 TELL, DON'T ASK:
 * O Despachador não pergunta "É urgente?" para decidir o que fazer.
 * Ele apenas ordena: entrega(nf). A inteligência fica encapsulada aqui.
 */

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;

public interface Entregador {
    void entrega(NotaFiscal nf);
}
