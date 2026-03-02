package capitulo5_encapsulamento.v3_lei_de_demeter;
/**
 * SOLUÇÃO v3: ServicoDeCobranca respeitando a Lei de Demeter.
 *
 * ✅ Fala apenas com seu amigo imediato: Fatura
 * ✅ Não sabe que existe um Cliente dentro da Fatura
 * ✅ Se Cliente mudar: zero impacto aqui
 */
public class ServicoDeCobranca {

    public void processaInadimplencia(FaturaEncapsulada fatura) {
        // ✅ Tell, Don't Ask: dizemos à Fatura o que fazer
        // ✅ Lei de Demeter respeitada: apenas um "ponto" na cadeia
        fatura.marcaComoInadimplente();
    }
}
