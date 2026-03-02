package capitulo5_encapsulamento.v4_solucao_completa;

import java.util.List;
/**
 * SOLUÇÃO v4: ProcessadorDeBoletos limpo.
 *
 * Compare com o v1:
 *   ❌ v1: fatura.getPagamentos().add(pagamento) + if(total >= ...) setPago(true)
 *   ✅ v4: fatura.adicionaPagamento(pagamento)
 *
 * Essa classe agora só faz o que é dela:
 * transformar boletos em pagamentos e entregá-los à Fatura.
 *
 * A regra de "quando pagar"? Não é problema dela.
 * O ProcessadorDeCartaoDeCredito vai usar o mesmo adicionaPagamento().
 * O ProcessadorDePix também. Nenhum deles vai duplicar lógica.
 */
public class ProcessadorDeBoletos {

    public void processa(List<Boleto> boletos, Fatura fatura) {
        for (Boleto boleto : boletos) {
            Pagamento pagamento = new Pagamento(
                    boleto.getValor(),
                    MeioDePagamento.BOLETO
            );
            // ✅ Tell, Don't Ask: só dizemos o que fazer
            fatura.adicionaPagamento(pagamento);
        }
    }
}
