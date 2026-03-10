package capitulo7_interfaces_magras.v3_tributavel;

import java.util.List;

/**
 * SOLUÇÃO v3: Interface mínima — Tributavel.
 *
 * Aniche, Cap. 7, seção 7.2:
 * O CalculadorDeImposto precisa apenas de itens para calcular.
 * Receber NotaFiscal inteira seria acoplamento desnecessário.
 *
 * Criar a interface Tributavel resolve três problemas:
 *   1. Acoplamento: dependemos de Tributavel (estável) e não de NotaFiscal (complexa)
 *   2. Clareza: o parâmetro tem semântica — não é "qualquer lista", é "algo tributável"
 *   3. Reúso: qualquer classe que implemente Tributavel pode ser tributada
 */
public interface Tributavel {
    List<Item> itensASeremTributados();
}
