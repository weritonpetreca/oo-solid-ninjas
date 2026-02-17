package capitulo3_acoplamento.v3_dip_completo.despachador_nf.usecases;

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports.CalculadorDeImposto;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports.Entregador;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports.Repositorio;

/**
 * 🐺 O COMANDANTE SUPREMO (USE CASE)
 *
 * Esta é a classe mais importante do nosso hexágono.
 * Ela orquestra o processo de despacho sem conhecer NENHUM detalhe
 * de infraestrutura.
 *
 * ⚔️ COMPARE COM AS VERSÕES ANTERIORES:
 *
 * V1 — Conhecia: EnviadorDeEmail, NotaFiscalDao (concretos)
 * V2 — Conhecia: CalculadorDeImposto, EntregadorDeNFs, NFDao (concretos)
 * V3 — Conhece: CalculadorDeImposto, Entregador, Repositorio (INTERFACES)
 *
 * 🛡️ DIP COMPLETO:
 * Repare nos imports desta classe. Ela só importa:
 * - domain/  (domínio puro)
 * - ports/   (abstrações estáveis)
 * Jamais importa algo de adapters/.
 * Isso é a garantia arquitetural do Hexagonal.
 *
 * 🧪 TESTABILIDADE:
 * Para testar esta classe, mockamos apenas 3 interfaces.
 * Nenhuma dependência de banco, correios ou qualquer infraestrutura.
 */

public class DespachadorDeNotasFiscais {

    private final CalculadorDeImposto impostos;
    private final Entregador entregador;
    private final Repositorio repositorio;

    public DespachadorDeNotasFiscais(
            CalculadorDeImposto impostos,
            Entregador entregador,
            Repositorio repositorio
    ) {
        this.impostos = impostos;
        this.entregador = entregador;
        this.repositorio = repositorio;
    }

    public void processa(NotaFiscal nf) {
        double imposto = impostos.para(nf);
        nf.setImposto(imposto);

        entregador.entrega(nf);
        repositorio.persiste(nf);
    }
}
