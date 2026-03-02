package capitulo5_encapsulamento.v4_solucao_completa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * SOLUÇÃO v4: Fatura encapsulada — modelo rico.
 *
 * Aplica tudo que o Aniche ensina no Cap. 5:
 *
 * ✅ adicionaPagamento() encapsula a lógica de pagamento E a regra de "quando pagar"
 *    → Qualquer processador (Boleto, Cartão, Pix) usa esse método
 *    → A regra muda? Muda em UM lugar só
 *
 * ✅ getPagamentos() retorna lista imutável (seção 5.7)
 *    → Ninguém pode adicionar/remover pagamentos pela lista
 *    → A única porta de entrada é adicionaPagamento()
 *
 * ✅ setPago() foi removido — a Fatura decide sozinha quando está paga
 *    → Não há como "forçar" o estado de fora
 *
 * ✅ valorTotalDosPagamentos() é privado — implementação escondida
 *
 * Teste do encapsulamento (seção 5.5):
 *   O quê? → adicionaPagamento: adiciona um pagamento à fatura
 *   Como?  → não sei, e não preciso saber ✅
 */
public class Fatura {

    private double valor;
    private boolean pago;
    private List<Pagamento> pagamentos;

    public Fatura(double valor) {
        this.valor = valor;
        this.pago = false;
        this.pagamentos = new ArrayList<>();
    }

    /**
     * ✅ Tell, Don't Ask na prática.
     * O caller diz: "adicione esse pagamento".
     * A Fatura cuida do resto — inclusive decidir se está paga.
     */
    public void adicionaPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
        if (valorTotalDosPagamentos() >= this.valor) {
            this.pago = true;
        }
    }

    public double getValor() {
        return valor;
    }

    public boolean isPago() {
        return pago;
    }

    /**
     * ✅ Lista imutável — ninguém modifica externamente (seção 5.7)
     */
    public List<Pagamento> getPagamentos() {
        return Collections.unmodifiableList(pagamentos);
    }

    /**
     * ✅ Implementação escondida. Private.
     * A peça pode ter o "desenho interno" alterado sem afetar ninguém.
     */
    private double valorTotalDosPagamentos() {
        double total = 0;
        for (Pagamento p : pagamentos) {
            total += p.getValor();
        }
        return total;
    }
}
