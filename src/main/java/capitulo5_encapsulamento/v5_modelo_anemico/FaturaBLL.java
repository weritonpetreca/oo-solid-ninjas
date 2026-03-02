package capitulo5_encapsulamento.v5_modelo_anemico;
/**
 * ANTIPADRÃO v5: FaturaBLL / FaturaBO / FaturaService / FaturaDelegate
 *
 * Todos esses nomes escondem o mesmo problema: comportamento separado dos dados.
 *
 * Essa classe acumula toda a lógica que deveria estar dentro de Fatura.
 * É o par inseparável do modelo anêmico: uma classe de dados + uma classe de funções.
 * Código C. Com classes Java.
 *
 * Consequências práticas:
 *   - Baixa coesão: FaturaBLL faz tudo relacionado a Fatura
 *   - Alto acoplamento: depende de todos os internals de Fatura
 *   - Duplicação garantida: outros BLLs vão repetir partes desta lógica
 *   - Testabilidade ruim: para testar finaliza(), preciso montar Fatura manualmente
 */
public class FaturaBLL {

    public void finaliza(Fatura fatura) {
        // Regra de finalização que deveria estar em Fatura.finaliza()
        if (fatura.getValor() > 0) {
            fatura.setPago(true);
        }
    }

    public double calculaImposto(Fatura fatura) {
        // Regra fiscal que deveria estar em Fatura.calculaImposto()
        if (fatura.getValor() > 10000.0) {
            return fatura.getValor() * 0.06;
        }
        return fatura.getValor() * 0.12;
    }

    public void adicionaPagamento(Fatura fatura, String descricaoPagamento) {
        // Regra de pagamento que deveria estar em Fatura.adicionaPagamento()
        fatura.getPagamentos().add(descricaoPagamento);
        double totalPago = fatura.getPagamentos().size() * 100.0; // simplificado
        if (totalPago >= fatura.getValor()) {
            fatura.setPago(true);
        }
    }
}
