package capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf;

/**
 * 📦 O ESPECIALISTA EM LOGÍSTICA
 *
 * Esta classe nasceu da refatoração "Extract Class".
 * Percebemos que {@link LeiDeEntrega} e {@link Correios} trabalhavam sempre juntos.
 *
 * 🛡️ ALTA COESÃO:
 * A responsabilidade de decidir "Como entregar" é inteiramente desta classe.
 * Ela encapsula a regra de negócio de urgência.
 *
 * ⚔️ ENCAPSULAMENTO:
 * O mundo exterior não precisa saber que usamos Correios ou que existe uma Lei.
 * Eles só precisam chamar o método `entrega()`.
 */
public class EntregadorDeNFs {
    private LeiDeEntrega lei;
    private Correios correios;

    public EntregadorDeNFs(LeiDeEntrega lei, Correios correios) {
        this.lei = lei;
        this.correios = correios;
    }

    public void entrega(NotaFiscal nf) {
        if (lei.deveEntregarUrgente(nf)) {
            correios.enviarPorSedex10(nf);
        } else {
            correios.enviarPorSedexComum(nf);
        }
    }
}
