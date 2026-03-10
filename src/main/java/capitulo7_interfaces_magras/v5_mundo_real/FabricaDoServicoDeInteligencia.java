package capitulo7_interfaces_magras.v5_mundo_real;

import java.util.List;

/**
 * Fábrica — monta o ServicoDeInteligencia com os meios corretos.
 *
 * Aniche, Cap. 7, seção 7.4:
 *   ✅ Centraliza a decisão de quais meios usar
 *   ✅ Altamente acoplada — mas estável e sem regras de negócio
 *   ✅ Troca de meio? Muda aqui. Só aqui.
 */
public class FabricaDoServicoDeInteligencia {

    /** Comunicação completa: corvo + pergaminho + cristal. */
    public ServicoDeInteligencia criaServicoCompleto() {
        return new ServicoDeInteligencia(List.of(
                new CorvoMensageiro(),
                new PergaminhoMagico(),
                new CristalDeComunicacao("GUILDA-PRINCIPAL")
        ));
    }

    /** Apenas corvo — para mensagens de baixa urgência. */
    public ServicoDeInteligencia criaServicoBasico() {
        return new ServicoDeInteligencia(List.of(new CorvoMensageiro()));
    }

    /** Emergência: pergaminho mágico + cristal. */
    public ServicoDeInteligencia criaServicoDeEmergencia() {
        return new ServicoDeInteligencia(List.of(
                new PergaminhoMagico(),
                new CristalDeComunicacao("EMERGENCIA-001")
        ));
    }
}
