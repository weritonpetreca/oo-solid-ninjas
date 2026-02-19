package capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports;

/**
 * 📜 PORTA: O COBRADOR DE IMPOSTOS
 *
 * "Porta de entrada" para qualquer lógica tributária.
 *
 * ⚔️ POR QUE É UMA INTERFACE AQUI E NÃO NA V2?
 * Na v2, CalculadorDeImposto era uma classe concreta.
 * O Despachador dependia dela diretamente — acoplamento instável.
 *
 * Agora o Despachador depende desta interface estável.
 * Se o governo mudar a alíquota (e no Brasil isso acontece toda semana),
 * criamos uma nova implementação sem tocar no Despachador.
 *
 * 🛡️ DIP: Módulos de alto nível (Despachador) não dependem de
 * módulos de baixo nível (ImpostoSimples). Ambos dependem desta abstração.
 */

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;

public interface CalculadorDeImposto {
    double para(NotaFiscal nf);
}
