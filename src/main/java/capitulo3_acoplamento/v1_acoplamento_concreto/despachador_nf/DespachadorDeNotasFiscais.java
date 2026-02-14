package capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf;

/**
 * 👹 O DESPACHADOR CENTRALIZADOR (GOD CLASS EM POTENCIAL)
 *
 * Esta classe sofre de "Acoplamento Eferente" (muitas dependências de saída).
 *
 * ⚠️ PROBLEMAS IDENTIFICADOS:
 * 1. Excesso de Dependências: Ele precisa de 4 classes no construtor para funcionar.
 * 2. Quebra de Encapsulamento: Ele conhece a regra de negócio da {@link LeiDeEntrega}.
 *    Ele pergunta: "É urgente?" e então decide qual método do {@link Correios} chamar.
 * 3. Dificuldade de Teste: Para testar essa classe, você precisa mockar 4 objetos.
 *
 * Se a regra de entrega mudar (ex: surgir Sedex 12), teremos que editar ESTA classe.
 * Isso viola o OCP (Open/Closed Principle).
 */
public class DespachadorDeNotasFiscais {

    private NFDao dao;
    private CalculadorDeImposto impostos;
    private LeiDeEntrega lei;
    private Correios correios;

    public DespachadorDeNotasFiscais(
            NFDao dao,
            CalculadorDeImposto impostos,
            LeiDeEntrega lei,
            Correios correios
    ) {
        this.dao = dao;
        this.impostos = impostos;
        this.lei = lei;
        this.correios = correios;
    }

    public void processa(NotaFiscal nf) {

        double imposto = impostos.para(nf);
        nf.setImposto(imposto);

        // LÓGICA EXPOSTA: O Despachador está tomando decisões de micro-gerenciamento.
        if (lei.deveEntregarUrgente(nf)) {
            correios.enviarPorSedex10(nf);
        } else {
            correios.enviarPorSedexComum(nf);
        }

        dao.persiste(nf);
    }
}
