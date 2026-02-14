package capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf;

/**
 * 📜 A REGRA EXPOSTA
 *
 * Nesta versão v1, esta classe é apenas uma consultora.
 * Ela diz "Sim/Não", mas obriga quem chama (o Despachador) a saber o que fazer com essa resposta.
 * Isso espalha a lógica de negócio pelo sistema.
 */
public class LeiDeEntrega {
    public boolean deveEntregarUrgente(NotaFiscal nf) {
        System.out.println("LeiDeEntrega: Verificando urgência...");
        return nf.getValor() > 1000.0;
    }
}
