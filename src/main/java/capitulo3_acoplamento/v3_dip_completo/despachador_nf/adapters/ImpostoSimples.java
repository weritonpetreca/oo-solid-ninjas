package capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters;

/**
 * 🔧 ADAPTER: IMPOSTO SIMPLES
 *
 * Implementação concreta do contrato {@link CalculadorDeImposto}.
 * Aplica a alíquota do Simples Nacional: 6%.
 *
 * ⚔️ ARQUITETURA HEXAGONAL:
 * Este arquivo vive na camada de "adapters" — a camada mais externa.
 * Ele conhece a porta (interface), mas a porta não sabe que ele existe.
 *
 * 🛡️ OCP (Open/Closed Principle):
 * Se o governo criar uma nova alíquota (ImpostoLucroReal, ImpostoPJ),
 * criamos um novo adapter. O Despachador permanece intocado.
 */

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports.CalculadorDeImposto;

public class ImpostoSimples implements CalculadorDeImposto {

    @Override
    public double para(NotaFiscal nf) {
        System.out.println("ImpostoSimples: Calculando 6% sobre " + nf.getValor());
        return nf.getValor() * 0.06;
    }
}
