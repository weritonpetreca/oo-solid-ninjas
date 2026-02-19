package capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf;

/**
 * 📜 A REGRA (LEI)
 *
 * Define a política de urgência.
 * É um detalhe de implementação que agora está escondido dentro do {@link EntregadorDeNFs}.
 */
public class LeiDeEntrega {
    public boolean deveEntregarUrgente(NotaFiscal nf) {
        System.out.println("LeiDeEntrega: Verificando urgência...");
        return nf.getValor() > 1000.0;
    }
}
