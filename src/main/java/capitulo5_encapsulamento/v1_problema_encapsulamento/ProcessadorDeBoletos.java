package capitulo5_encapsulamento.v1_problema_encapsulamento;

import java.util.List;

/**
 * PROBLEMA v1: ProcessadorDeBoletos sabe demais sobre Fatura.
 *
 * Dois problemas clássicos de encapsulamento (Aniche, Cap. 5):
 *
 * ❌ PROBLEMA 1 — Getter que expõe internals:
 *   fatura.getPagamentos().add(pagamento)
 *   O processador acessa a lista interna da Fatura diretamente.
 *   Se Fatura mudar como armazena pagamentos, isso quebra aqui.
 *
 * ❌ PROBLEMA 2 — Regra de negócio fora do dono:
 *   if(total >= fatura.getValor()) { fatura.setPago(true); }
 *   A regra de "quando uma fatura está paga" vive aqui fora.
 *   Amanhã o ProcessadorDeCartaoDeCredito vai copiar esse if.
 *   Se a regra mudar: CTRL+F em todo o sistema. Isso não é arquitetura.
 *
 * Viola o Tell, Don't Ask:
 *   ❌ Perguntamos getValor() → decidimos → mandamos setPago(true)
 *   ✅ Deveria ser: fatura, receba esse pagamento e cuide do resto
 */
public class ProcessadorDeBoletos {

    public void processa(List<Boleto> boletos, Fatura fatura) {

        double total = 0;

        for (Boleto boleto: boletos) {
            Pagamento pagamento = new Pagamento(
                    boleto.getValor(),
                    MeioDePagamento.BOLETO);

            // ❌ PROBLEMA 1: Acessa a lista interna diretamente
            fatura.getPagamentos().add(pagamento);

            total += boleto.getValor();
        }

        // ❌ PROBLEMA 2: Regra da Fatura vivendo no Processador
        // Esse if vai aparecer no ProcessadorDeCartaoDeCredito,
        // no ProcessadorDePix, no ProcessadorDeTransferencia...
        if (total >= fatura.getValor()) {
            fatura.setPago(true);
        }
    }
}
