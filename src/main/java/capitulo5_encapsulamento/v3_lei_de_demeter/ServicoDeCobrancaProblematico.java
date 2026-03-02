package capitulo5_encapsulamento.v3_lei_de_demeter;
/**
 * PROBLEMA v3: Viola a Lei de Demeter com getter encadeado.
 *
 * Aniche, Cap. 5, seção 5.6:
 *   "fatura.getCliente().marcaComoInadimplente()"
 *
 * O que acontece se Cliente mudar?
 *   → Este código quebra.
 *   → Todo código que faz fatura.getCliente().qualquerCoisa() quebra.
 *
 * O problema é que essa dependência em Cliente é INVISÍVEL:
 * ServicoDeCobranca parece depender apenas de Fatura,
 * mas na verdade depende de Fatura E de Cliente ao mesmo tempo.
 *
 * Lei de Demeter: "Fale apenas com seus amigos imediatos."
 *   ✅ Fatura é amigo imediato — ok falar com ela
 *   ❌ Cliente é amigo da Fatura, não meu — não devo falar com ele diretamente
 */
public class ServicoDeCobrancaProblematico {

    public void processaInadimplencia(FaturaProblematica fatura) {
        // ❌ LEI DE DEMETER VIOLADA
        // Estamos "atravessando" a Fatura para chegar no Cliente
        // Se Cliente mudar sua interface pública, este código quebra
        fatura.getCliente().marcaComoInadimplente();
    }
}
